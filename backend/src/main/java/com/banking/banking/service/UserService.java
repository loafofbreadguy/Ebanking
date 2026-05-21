package com.banking.banking.service;

import com.banking.banking.dto.UserResponseDto;
import com.banking.banking.dto.UserRequestDto;
import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto getUserById();

    UserResponseDto updateUser(UserRequestDto request);

    void deleteUser(Long id);

    void verifyUser(String token);

    void resendVerificationEmail();
}
