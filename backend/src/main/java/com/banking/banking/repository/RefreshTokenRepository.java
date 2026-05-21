package com.banking.banking.repository;

import com.banking.banking.model.RefreshToken;

import com.banking.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteAllByUserEmail(String email);
    void deleteByUser(User user);
}