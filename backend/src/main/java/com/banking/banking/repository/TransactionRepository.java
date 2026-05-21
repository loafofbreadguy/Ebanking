package com.banking.banking.repository;

import com.banking.banking.model.Transaction;
import com.banking.banking.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findBySenderWallet_IdOrReceiverWallet_Id(
            Long senderWalletId,
            Long receiverWalletId,
            Pageable pageable
    );

    Transaction findByTransactionRef(String transactionRef);

    void deleteBySenderWalletOrReceiverWallet(
            Wallet senderWallet,
            Wallet receiverWallet
    );
}