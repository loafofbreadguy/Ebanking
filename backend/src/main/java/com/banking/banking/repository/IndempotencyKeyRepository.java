package com.banking.banking.repository;

import com.banking.banking.model.IdempotencyKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndempotencyKeyRepository extends JpaRepository<IdempotencyKey, Long> {

}
