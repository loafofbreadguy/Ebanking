package com.banking.banking.dto;
import com.banking.banking.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}