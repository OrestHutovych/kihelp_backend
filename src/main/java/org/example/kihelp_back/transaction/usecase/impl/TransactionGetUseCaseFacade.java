package org.example.kihelp_back.transaction.usecase.impl;

import org.example.kihelp_back.transaction.dto.TransactionResponseDto;
import org.example.kihelp_back.transaction.mapper.TransactionToTransactionResponseMapper;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionGetUseCaseFacade implements TransactionGetUseCase {
    private final TransactionService transactionService;
    private final TransactionToTransactionResponseMapper transactionToTransactionResponseMapper;

    public TransactionGetUseCaseFacade(TransactionService transactionService,
                                       TransactionToTransactionResponseMapper transactionToTransactionResponseMapper) {
        this.transactionService = transactionService;
        this.transactionToTransactionResponseMapper = transactionToTransactionResponseMapper;
    }

    @Override
    public List<TransactionResponseDto> getAllTransactionsByUserTelegramId(String telegramId) {
        var transactions = transactionService.findTransactionsByUserTelegramId(telegramId);

        
        return transactions.stream()
                .map(transactionToTransactionResponseMapper::map)
                .toList();
    }
}
