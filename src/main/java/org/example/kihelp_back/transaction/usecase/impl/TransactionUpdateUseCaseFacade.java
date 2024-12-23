package org.example.kihelp_back.transaction.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.mapper.TransactionDepositDtoToTransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.example.kihelp_back.user.exception.UserNotFoundException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_NOT_FOUND_BY_TG_ID;

@Component
@Slf4j
public class TransactionUpdateUseCaseFacade implements TransactionUpdateUseCase {
    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionDepositDtoToTransactionMapper transactionDepositDtoToTransactionMapper;

    public TransactionUpdateUseCaseFacade(
            TransactionService transactionService,
            UserService userService,
            TransactionDepositDtoToTransactionMapper transactionDepositDtoToTransactionMapper) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionDepositDtoToTransactionMapper = transactionDepositDtoToTransactionMapper;
    }

    @Override
    public void depositWallet(TransactionDepositDto request) {
        User user = userService.findByTelegramId(request.telegramId())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_BY_TG_ID, request.telegramId())
                ));

        log.info("Attempting to map TransactionCreateDto: '{}' to Transaction.", request.transactionId());
        Transaction transaction = transactionDepositDtoToTransactionMapper.map(request, user);

        transactionService.deposit(transaction);
    }
}
