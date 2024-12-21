package org.example.kihelp_back.transaction.service;

import org.example.kihelp_back.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);
    List<Transaction> findTransactionsByUserTelegramId(String telegramId);
    void deleteTransactionByTelegramId(String telegramId);
}
