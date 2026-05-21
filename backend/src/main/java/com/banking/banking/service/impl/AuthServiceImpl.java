package com.banking.banking.service.impl;

import com.banking.banking.dto.LoginRequest;
import com.banking.banking.dto.LoginResponse;
import com.banking.banking.model.RefreshToken;
import com.banking.banking.model.User;
import com.banking.banking.repository.UserRepository;
import com.banking.banking.security.CookieService;
import com.banking.banking.security.JwtUtils;
import com.banking.banking.service.AuthService;
import com.banking.banking.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final CookieService cookieService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            RefreshTokenService refreshTokenService,
            CookieService cookieService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.cookieService = cookieService;
    }

    @Override
    @Transactional
    public void login(LoginRequest loginRequest, HttpServletResponse response) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate tokens
        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        // Add cookies
        cookieService.addAccessTokenCookie(response, accessToken);
        cookieService.addRefreshTokenCookie(response, refreshToken.getToken());
    }

    @Override
    @Transactional
    public void logout(HttpServletResponse response, String refreshToken) {

        if (refreshToken != null) {
            refreshTokenService.deleteRefreshToken(refreshToken);
        }

        cookieService.clearAuthCookies(response);
    }

    @Override
    @Transactional
    public LoginResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String refreshTokenFromCookie =
                cookieService.getRefreshTokenFromRequest(request);

        if (refreshTokenFromCookie == null) {
            throw new RuntimeException("Refresh token not found");
        }

        // Verify refresh token
        RefreshToken oldToken =
                refreshTokenService.verifyRefreshToken(refreshTokenFromCookie);

        // Get user email
        String email = oldToken.getUser().getEmail();

        // Generate new access token
        String newAccessToken =
                jwtUtils.generateAccessToken(email);

        // Rotate refresh token
        refreshTokenService.deleteRefreshToken(refreshTokenFromCookie);

        RefreshToken newRefreshTokenEntity =
                refreshTokenService.createRefreshToken(email);

        // Add new cookies
        cookieService.addAccessTokenCookie(response, newAccessToken);

        cookieService.addRefreshTokenCookie(
                response,
                newRefreshTokenEntity.getToken()
        );

        return new LoginResponse("Token refreshed successfully");
    }
}