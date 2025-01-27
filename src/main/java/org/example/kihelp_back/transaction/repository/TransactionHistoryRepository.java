package org.example.kihelp_back.transaction.repository;

import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTransactionId(String transactionId);
    @Transactional(readOnly = true)
    Optional<Transaction> findByTransactionId(String transactionId);

    @Transactional(readOnly = true)
    @Query("""
       SELECT t FROM Transaction t
       WHERE t.user.id = :userId AND t.status = 'COMPLETED'
    """)
    Page<Transaction> findAllCompletedTransactionByUserId(@Param("userId") Long userId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("""
       SELECT t FROM Transaction t
       WHERE t.status = 'IN_PROGRESS' AND t.type = 'WITHDRAW'
    """)
    List<Transaction> findAllInProgressWithdrawTransactions();
}