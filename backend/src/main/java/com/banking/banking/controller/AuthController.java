package com.banking.banking.controller;

import com.banking.banking.dto.LoginResponse;
import com.banking.banking.security.CookieService;
import com.banking.banking.service.AuthService;
import com.banking.banking.dto.LoginRequest;
import com.banking.banking.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
<<<<<<< HEAD
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

=======
import org.springframework.web.bind.annotation.*;

>>>>>>> bba3b811de6f153d86861a51266f227099580679
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final  AuthService authService;
    private final CookieService cookieService;

    public AuthController(CookieService cookieService, AuthService authService){
        this.cookieService = cookieService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest,
                                               HttpServletResponse response) {
        authService.login(loginRequest, response);
        ApiResponse<LoginResponse> loginResponse = new ApiResponse<>(
                true,
                "Login Successful",
                new LoginResponse("Login successful")
        );
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<LoginResponse>> logout(HttpServletRequest request,
                                                HttpServletResponse response) {
        String refreshToken = cookieService.getRefreshTokenFromRequest(request);
        authService.logout(response, refreshToken);
        ApiResponse<LoginResponse> loginResponse = new ApiResponse<>(
                true,
                "Logout Successful",
                new LoginResponse("Logout successful")
        );
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(HttpServletRequest request,
                                                      HttpServletResponse response) {

        ApiResponse<LoginResponse> loginResponse = new ApiResponse<>(
                true,
                "Token Refresh Successful",
                authService.refreshToken(request, response)
        );
        return ResponseEntity.ok(loginResponse);
    }

<<<<<<< HEAD
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        Map<String, Object> user = new HashMap<>();

        user.put("email", userDetails.getUsername());
        user.put("authenticated", true);

        return ResponseEntity.ok(user);
    }
=======
>>>>>>> bba3b811de6f153d86861a51266f227099580679

}