package com.banking.banking.service.impl;

import com.banking.banking.dto.BalanceResponse;
import com.banking.banking.enums.TransactionStatus;
import com.banking.banking.enums.TransactionType;
import com.banking.banking.model.Transaction;
import com.banking.banking.model.User;
import com.banking.banking.model.Wallet;
import com.banking.banking.repository.TransactionRepository;
import com.banking.banking.repository.UserRepository;
import com.banking.banking.repository.WalletRepository;
import com.banking.banking.service.WalletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(WalletRepository walletRepository,
                             UserRepository userRepository,
                             TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ---------------- CREATE WALLET ----------------
    @Override
    @Transactional
    public void createWalletForUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCreatedAt(LocalDateTime.now());

        walletRepository.save(wallet);
    }

    // ---------------- DEPOSIT ----------------
    @Override
    @Transactional
    public void deposit(BigDecimal amount) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));

        walletRepository.save(wallet);

        // create transaction record
        Transaction tx = new Transaction();
        tx.setTransactionRef(UUID.randomUUID().toString());
        tx.setSenderWallet(null);
        tx.setReceiverWallet(wallet);
        tx.setAmount(amount);
        tx.setType(TransactionType.DEPOSIT);
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setDescription("Wallet deposit");
        tx.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(tx);
    }

    // ---------------- WITHDRAW ----------------
    @Override
    @Transactional
    public void withdraw(BigDecimal amount) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        walletRepository.save(wallet);

        // create transaction record
        Transaction tx = new Transaction();
        tx.setTransactionRef(UUID.randomUUID().toString());
        tx.setSenderWallet(wallet);
        tx.setReceiverWallet(null);
        tx.setAmount(amount);
        tx.setType(TransactionType.WITHDRAW);
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setDescription("Wallet withdraw");
        tx.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(tx);
    }

    // ---------------- GET BALANCE ----------------
    @Override
    public BalanceResponse getBalance() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        return new BalanceResponse(wallet.getBalance());
    }
}