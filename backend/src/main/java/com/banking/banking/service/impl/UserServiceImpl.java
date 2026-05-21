package com.banking.banking.service.impl;

import com.banking.banking.dto.UserRequestDto;
import com.banking.banking.dto.UserResponseDto;

import com.banking.banking.enums.UserRole;

import com.banking.banking.model.User;
import com.banking.banking.model.VerificationToken;

import com.banking.banking.repository.UserRepository;
import com.banking.banking.repository.VerificationTokenRepository;
import com.banking.banking.repository.ComplaintRepository;
import com.banking.banking.repository.RefreshTokenRepository;
import com.banking.banking.repository.TransactionRepository;
import com.banking.banking.repository.WalletRepository;

import com.banking.banking.service.EmailService;
import com.banking.banking.service.UserService;
import com.banking.banking.service.WalletService;
import com.banking.banking.service.AuthService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;

    private final ComplaintRepository complaintRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           WalletService walletService,
                           AuthService authService,
                           VerificationTokenRepository verificationTokenRepository,
                           EmailService emailService,
                           ComplaintRepository complaintRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           WalletRepository walletRepository,
                           TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;

        this.complaintRepository = complaintRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto request) {

        User user = new User();
        user.setName(request.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setVerified(false);

        User savedUser = userRepository.save(user);

        walletService.createWalletForUser(savedUser.getId());

        // generate verification token for is_validated
        String token = UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setUser(savedUser);
        vt.setExpiryDate(LocalDateTime.now().plusMinutes(30));

        verificationTokenRepository.save(vt);

        // send email
        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponseDto getUserById() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToResponse(user);
    }

    // UPDATE USER
    @Override
    public UserResponseDto updateUser(UserRequestDto request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    // DELETE USER
    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // delete all the linked tables
        complaintRepository.deleteByUser(user);
        refreshTokenRepository.deleteByUser(user);
        verificationTokenRepository.findByUser(user)
                .ifPresent(verificationTokenRepository::delete);
        walletRepository.findByUser(user)
                .ifPresent(wallet -> {

                    transactionRepository.deleteBySenderWalletOrReceiverWallet(wallet, wallet);

                    walletRepository.delete(wallet);
                });
        userRepository.delete(user);
    }

    @Override
    public void verifyUser(String token) {

        VerificationToken vt = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (vt.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = vt.getUser();
        user.setVerified(true);

        userRepository.save(user);

        verificationTokenRepository.delete(vt);
    }

    @Override
    public void resendVerificationEmail() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isVerified()) {
            throw new RuntimeException("User already verified");
        }

        verificationTokenRepository.findByUser(user)
                .ifPresent(existingToken -> {
                    if (existingToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                        verificationTokenRepository.delete(existingToken);
                    }
                    else {
                        throw new RuntimeException("Token is still valid, cannot resend yet");
                    }
                });


        String token = UUID.randomUUID().toString();

        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        vt.setUser(user);
        vt.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        verificationTokenRepository.save(vt);

        emailService.sendVerificationEmail(user.getEmail(), token);
    }


    private UserResponseDto mapToResponse(User user) {

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }

}