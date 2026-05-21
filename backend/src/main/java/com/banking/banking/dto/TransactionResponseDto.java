package com.banking.banking.dto;

import com.banking.banking.enums.TransactionStatus;
import com.banking.banking.enums.TransactionType;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDto {

    private String transactionRef;
    private BigDecimal amount;
    private TransactionType type;
    private TransactionStatus status;
    private String description;
    private LocalDateTime createdAt;

    private Long senderWalletId;
    private Long receiverWalletId;
}