package com.banking.banking.service;

import com.banking.banking.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String email);

    RefreshToken verifyRefreshToken(String token);

    void deleteRefreshToken(String token);
}