package com.banking.banking.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.banking.banking.enums.AuditAction;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction_audit_logs")
public class TransactionAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionRef;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    private String message;

    private LocalDateTime createdAt;

    // getters and setters
}