package com.banking.banking.repository;

import com.banking.banking.model.TransactionAuditLog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionAuditLogRepository
        extends JpaRepository<TransactionAuditLog, Long> {
}