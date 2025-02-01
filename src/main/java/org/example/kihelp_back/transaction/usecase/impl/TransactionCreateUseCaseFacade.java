package org.example.kihelp_back.transaction.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionCreateUseCaseFacade implements TransactionCreateUseCase {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionCreateUseCaseFacade(
            TransactionService transactionService,
            TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Transaction createDepositTransaction(String telegramId, TransactionCreateDto request) {
        Transaction transaction = transactionMapper.toEntity(request);
        return transactionService.deposit(transaction);
    }

    @Override
    public Transaction createWithdrawTransaction(TransactionWithdrawDto dto) {
        Transaction withdrawTransaction = transactionMapper.toEntity(dto);
        return transactionService.withdraw(withdrawTransaction);
    }
}
