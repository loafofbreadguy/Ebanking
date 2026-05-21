package com.banking.banking.controller;

import com.banking.banking.dto.ApiResponse;
import com.banking.banking.dto.TransactionResponseDto;
import com.banking.banking.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    @PreAuthorize("isAuthenticated() and @userSecurity.isVerified(authentication)")
    public ResponseEntity<ApiResponse<String>> transfer(
            @RequestParam Long receiverUserId,
            @RequestParam BigDecimal amount,
            @RequestParam String description
    ) {

        transactionService.transfer(receiverUserId, amount, description);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Transfer successful",
                "Money transferred successfully"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getalltransaction")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Page<TransactionResponseDto>>> getAllTransaction(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {

        Page<TransactionResponseDto> transactions =
                transactionService.getAllTransaction(page, size);

        ApiResponse<Page<TransactionResponseDto>> response =
                new ApiResponse<>(
                        true,
                        "All Transactions fetched successfully",
                        transactions
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbyid/{transaction_ref}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<TransactionResponseDto>> getTransactionById(
            @PathVariable String transaction_ref
    ) {

        TransactionResponseDto transaction = transactionService.getTransactionById(transaction_ref);

        ApiResponse<TransactionResponseDto> response = new ApiResponse<>(
                true,
                "Transaction fetched successfully",
                transaction
        );

        return ResponseEntity.ok(response);
    }
}