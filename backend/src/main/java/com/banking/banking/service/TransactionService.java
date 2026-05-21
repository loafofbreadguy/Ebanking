package com.banking.banking.service;

import com.banking.banking.dto.TransactionResponseDto;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;

public interface TransactionService {

    void transfer(Long receiverUserId,
                  BigDecimal amount,
                  String description);

    Page<TransactionResponseDto> getAllTransaction(
            int page,
            int size
    );

    TransactionResponseDto getTransactionById(String transactionRef);
}