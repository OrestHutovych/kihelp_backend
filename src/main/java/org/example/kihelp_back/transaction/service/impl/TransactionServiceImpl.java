package org.example.kihelp_back.transaction.service.impl;

import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.repository.TransactionRepository;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.transaction.util.ErrorMessage.TRANSACTION_EXISTS;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findTransactionsByUserTelegramId(String telegramId) {
        return transactionRepository.findAllByUserTelegramId(telegramId);
    }

    @Override
    public void deleteTransactionByTelegramId(String telegramId) {
        var transactions = findTransactionsByUserTelegramId(telegramId);

        transactionRepository.deleteAll(transactions);
    }
}
