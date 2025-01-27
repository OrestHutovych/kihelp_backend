package org.example.kihelp_back.transaction.service;

import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.transaction.repository.TransactionHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.kihelp_back.transaction.util.TransactionErrorMessage.*;

@Service
public class TransactionService {
    private final TransactionHistoryRepository transactionRepository;

    public TransactionService(TransactionHistoryRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction deposit(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        return transactionRepository.save(transaction);
    }

    public void toggleWithdrawStatus(String transactionId) {
        Transaction transaction = findTransactionByTransactionId(transactionId);

        if(!transaction.getType().equals(TransactionType.WITHDRAW)){
            throw new IllegalArgumentException(TRANSACTION_TYPE_IS_NOT_WITHDRAW);
        }

        if(!transaction.getStatus().equals(TransactionStatus.IN_PROGRESS)) {
            throw new IllegalArgumentException(TRANSACTION_STATUS_NOT_IN_PROGRESS);
        }

        transaction.setStatus(TransactionStatus.COMPLETED);

        transactionRepository.save(transaction);
    }

    public Transaction findTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException
                        (String.format(TRANSACTION_NOT_FOUND, transactionId))
                );
    }

    public Page<Transaction> findCompletedTransactionsByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findAllCompletedTransactionByUserId(userId, pageable);
    }
}
