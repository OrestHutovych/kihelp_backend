package org.example.kihelp_back.transaction.usecase;

import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.dto.TransactionPageDto;

import java.util.Collection;

public interface TransactionGetUseCase {
    Collection<TransactionDto> getAllTransactionsByUserTelegramId(String telegramId, TransactionPageDto pageDto);
}
