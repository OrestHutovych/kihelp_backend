package org.example.kihelp_back.transaction.usecase;

import org.example.kihelp_back.transaction.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionGetUseCase {
    List<TransactionResponseDto> getAllTransactionsByUserTelegramId(String telegramId);
}
