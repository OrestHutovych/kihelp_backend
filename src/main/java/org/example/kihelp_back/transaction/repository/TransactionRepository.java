package org.example.kihelp_back.transaction.repository;

import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTransactionId(String transactionId);
    @Transactional(readOnly = true)
    List<Transaction> findAllByUserTelegramId(String telegramId);
    @Transactional(readOnly = true)
    Optional<Transaction> findByTransactionId(String transactionId);
}