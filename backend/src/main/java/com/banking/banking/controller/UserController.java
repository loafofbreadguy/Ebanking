package com.banking.banking.controller;

import com.banking.banking.dto.ApiResponse;
import com.banking.banking.dto.UserRequestDto;
import com.banking.banking.dto.UserResponseDto;
import com.banking.banking.service.UserService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody UserRequestDto request
    ) {

        UserResponseDto user = userService.createUser(request);

        ApiResponse<UserResponseDto> response = new ApiResponse<>(
                true,
                "User Registered successful",
                user
        );

        return ResponseEntity.ok(response);
    }

    // GET USER
    @GetMapping("/fetch/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById() {

        UserResponseDto user = userService.getUserById();

        ApiResponse<UserResponseDto> response = new ApiResponse<>(
                true,
                "User Update successful",
                user
        );

        return ResponseEntity.ok(response);
    }

    // UPDATE USER
    @PutMapping("/update/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @Valid @RequestBody UserRequestDto request
    ) {

        UserResponseDto user = userService.updateUser(request);

        ApiResponse<UserResponseDto> response = new ApiResponse<>(
                true,
                "User Update successful",
                user
        );

        return ResponseEntity.ok(response);
    }

    // DELETE USER
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "User Deletion successful",
                "User Verified successfully"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestParam String token) {

        userService.verifyUser(token);

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Verification successful",
                "User Verified successfully"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<ApiResponse<String>> resendVerificationEmail()
    {

        userService.resendVerificationEmail();

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Verification Link sent",
                "Verification link sent successfully"
        );

        return ResponseEntity.ok(response);
    }
}