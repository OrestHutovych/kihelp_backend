package org.example.kihelp_back.transaction.usecase.impl;

import org.example.kihelp_back.transaction.dto.TransactionWithdrawStatusDto;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TransactionUpdateUseCaseFacade implements TransactionUpdateUseCase {
    private final TransactionService transactionService;

    public TransactionUpdateUseCaseFacade(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void toggleWithdrawStatus(TransactionWithdrawStatusDto request) {
        transactionService.toggleWithdrawStatus(request.transactionId());
    }
}
