package com.banking.banking.controller;

import com.banking.banking.dto.BalanceResponse;
import com.banking.banking.service.WalletService;
import com.banking.banking.dto.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // ---------------- DEPOSIT ----------------
    @PostMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BigDecimal>> deposit(
            @RequestParam BigDecimal amount) {

        walletService.deposit(amount);
        ApiResponse <BigDecimal> response = new ApiResponse<>(
                true,
                "Balance Loaded Successfully",
                amount
        );

        return ResponseEntity.ok(response);
    }

    // ---------------- WITHDRAW ----------------
    @PostMapping("/withdraw")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BigDecimal>> withdraw(
            @RequestParam BigDecimal amount) {

        walletService.withdraw(amount);

        walletService.deposit(amount);
        ApiResponse <BigDecimal> response = new ApiResponse<>(
                true,
                "Balance Withdrawn Successfully",
                amount
        );

        return ResponseEntity.ok(response);
    }

    // ---------------- BALANCE ----------------
    @GetMapping("/balance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance() {
        ApiResponse<BalanceResponse> response = new ApiResponse<>(
                true,
                "Balance Fetched Successfully",
                walletService.getBalance()
        );
        return ResponseEntity.ok(response);
    }
}