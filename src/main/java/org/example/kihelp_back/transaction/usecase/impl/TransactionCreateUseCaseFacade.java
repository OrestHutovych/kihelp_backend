package org.example.kihelp_back.transaction.usecase.impl;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.mapper.TransactionCreateDtoToTransactionMapper;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_NOT_FOUND_BY_TG_ID;

@Component
public class TransactionCreateUseCaseFacade implements TransactionCreateUseCase {
    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionCreateDtoToTransactionMapper transactionCreateDtoToTransactionMapper;

    public TransactionCreateUseCaseFacade(TransactionService transactionService,
                                          UserService userService,
                                          TransactionCreateDtoToTransactionMapper transactionCreateDtoToTransactionMapper) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionCreateDtoToTransactionMapper = transactionCreateDtoToTransactionMapper;
    }

    @Override
    public void createTransaction(TransactionCreateDto request) {
        var user = userService.findByTelegramId(request.telegramId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_BY_TG_ID, request.telegramId())
                ));
        var transaction = transactionCreateDtoToTransactionMapper.map(request, user);

        transactionService.save(transaction);
    }
}
