package com.banking.banking.service;

import com.banking.banking.dto.BalanceResponse;

import java.math.BigDecimal;

public interface WalletService {

    void createWalletForUser(Long userId);

    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);

    BalanceResponse getBalance();
}