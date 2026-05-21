package com.banking.banking.service;

import com.banking.banking.dto.LoginRequest;
import com.banking.banking.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void login(LoginRequest loginRequest, HttpServletResponse response);

    void logout(HttpServletResponse response, String refreshToken);

    LoginResponse refreshToken(HttpServletRequest request,
                               HttpServletResponse response);
}