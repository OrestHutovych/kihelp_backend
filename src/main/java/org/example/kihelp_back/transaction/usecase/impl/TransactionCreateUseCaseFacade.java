package org.example.kihelp_back.transaction.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionCreateMapper;
import org.example.kihelp_back.transaction.mapper.TransactionWithdrawMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionCreateUseCaseFacade implements TransactionCreateUseCase {
    private final TransactionService transactionService;
    private final TransactionCreateMapper transactionCreateMapper;
    private final TransactionWithdrawMapper transactionWithdrawMapper;

    public TransactionCreateUseCaseFacade(
            TransactionService transactionService,
            TransactionCreateMapper transactionCreateMapper,
            TransactionWithdrawMapper transactionWithdrawMapper) {
        this.transactionService = transactionService;
        this.transactionCreateMapper = transactionCreateMapper;
        this.transactionWithdrawMapper = transactionWithdrawMapper;
    }

    @Override
    public Transaction createDepositTransaction(String telegramId, TransactionCreateDto request) {
        Transaction transaction = transactionCreateMapper.toEntity(request);
        return transactionService.deposit(transaction);
    }

    @Override
    public Transaction createWithdrawTransaction(TransactionWithdrawDto dto) {
        Transaction withdrawTransaction = transactionWithdrawMapper.toData(dto);
        return transactionService.withdraw(withdrawTransaction);
    }
}
