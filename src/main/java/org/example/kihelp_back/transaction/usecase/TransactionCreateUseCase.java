package org.example.kihelp_back.transaction.usecase;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionCreateUseCase {
    Transaction createWithdrawTransaction(TransactionWithdrawDto request);
    Transaction createDepositTransaction(String telegramId, TransactionCreateDto request);
}
