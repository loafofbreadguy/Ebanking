package com.banking.banking.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.banking.banking.enums.UserRole;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isVerified;

    private LocalDateTime createdAt;
}