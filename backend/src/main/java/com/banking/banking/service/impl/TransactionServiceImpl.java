package com.banking.banking.service.impl;

import com.banking.banking.enums.AuditAction;
import com.banking.banking.enums.TransactionStatus;
import com.banking.banking.enums.TransactionType;
import com.banking.banking.model.Transaction;
import com.banking.banking.model.TransactionAuditLog;
import com.banking.banking.model.User;
import com.banking.banking.model.Wallet;
import com.banking.banking.dto.TransactionResponseDto;
import com.banking.banking.repository.TransactionAuditLogRepository;
import com.banking.banking.repository.TransactionRepository;
import com.banking.banking.repository.UserRepository;
import com.banking.banking.repository.WalletRepository;
import com.banking.banking.service.TransactionService;


import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionAuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(
            WalletRepository walletRepository,
            TransactionRepository transactionRepository,
            TransactionAuditLogRepository auditLogRepository,
            UserRepository userRepository
    ) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void transfer(Long receiverUserId,
                         BigDecimal amount,
                         String description) {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet senderWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));


        Wallet receiverWallet = walletRepository.findByUserId(receiverUserId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver wallet not found"));

        if (user.getId() == receiverUserId){
            throw new RuntimeException("Cant give balance to same user");
        }

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // DEBIT
        senderWallet.setBalance(
                senderWallet.getBalance().subtract(amount)
        );

        // CREDIT
        receiverWallet.setBalance(
                receiverWallet.getBalance().add(amount)
        );

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        String ref = UUID.randomUUID().toString();

        // SAVE TRANSACTION
        Transaction transaction = new Transaction();

        transaction.setTransactionRef(ref);
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDescription(description);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        // SAVE AUDIT LOG
        TransactionAuditLog auditLog = new TransactionAuditLog();

        auditLog.setTransactionRef(ref);
        auditLog.setAction(AuditAction.CREATED);
        auditLog.setMessage("Transfer completed successfully");
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    @Override
    public Page<TransactionResponseDto> getAllTransaction(
            int page,
            int size
    ) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<Transaction> transactions =
                transactionRepository
                        .findBySenderWallet_IdOrReceiverWallet_Id(
                                wallet.getId(),
                                wallet.getId(),
                                pageable
                        );

        List<TransactionResponseDto> dtoList =
                transactions.getContent().stream().map(transaction -> {

                    TransactionResponseDto dto = new TransactionResponseDto();

                    dto.setTransactionRef(transaction.getTransactionRef());
                    dto.setAmount(transaction.getAmount());
                    dto.setType(transaction.getType());
                    dto.setStatus(transaction.getStatus());
                    dto.setDescription(transaction.getDescription());
                    dto.setCreatedAt(transaction.getCreatedAt());

                    dto.setSenderWalletId(
                            transaction.getSenderWallet() != null
                                    ? transaction.getSenderWallet().getId()
                                    : null
                    );

                    dto.setReceiverWalletId(
                            transaction.getReceiverWallet() != null
                                    ? transaction.getReceiverWallet().getId()
                                    : null
                    );

                    return dto;

                }).toList();

        return new PageImpl<>(
                dtoList,
                pageable,
                transactions.getTotalElements()
        );
    }

    @Override
    public TransactionResponseDto getTransactionById(String TransactionRef) {

        Transaction transaction = transactionRepository.findByTransactionRef(TransactionRef);

        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setTransactionRef(transaction.getTransactionRef());
        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setStatus(transaction.getStatus());
        dto.setDescription(transaction.getDescription());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setSenderWalletId(
                transaction.getSenderWallet() != null
                        ? transaction.getSenderWallet().getId()
                        : null
        );

        dto.setReceiverWalletId(
                transaction.getReceiverWallet() != null
                        ? transaction.getReceiverWallet().getId()
                        : null
        );
        return dto;
    }
}