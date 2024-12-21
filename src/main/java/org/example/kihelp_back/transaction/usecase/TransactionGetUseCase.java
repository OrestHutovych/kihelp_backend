package org.example.kihelp_back.transaction.usecase;

import org.example.kihelp_back.transaction.dto.TransactionResponse;

import java.util.List;

public interface TransactionGetUseCase {
    List<TransactionResponse> getAllTransactionsByUserTelegramId(String telegramId);
}
