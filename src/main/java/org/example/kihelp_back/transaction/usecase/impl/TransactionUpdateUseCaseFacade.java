package org.example.kihelp_back.transaction.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionDepositDtoToTransactionMapper;
import org.example.kihelp_back.transaction.mapper.TransactionWithdrawDtoToTransactionMapper;
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
    private final TransactionWithdrawDtoToTransactionMapper transactionWithdrawDtoToTransactionMapper;

    public TransactionUpdateUseCaseFacade(
            TransactionService transactionService,
            UserService userService,
            TransactionDepositDtoToTransactionMapper transactionDepositDtoToTransactionMapper,
            TransactionWithdrawDtoToTransactionMapper transactionWithdrawDtoToTransactionMapper) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionDepositDtoToTransactionMapper = transactionDepositDtoToTransactionMapper;
        this.transactionWithdrawDtoToTransactionMapper = transactionWithdrawDtoToTransactionMapper;
    }

    @Override
    public void depositWallet(String telegramId, TransactionDepositDto request) {
        User user = userService.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_BY_TG_ID, telegramId)
                ));

        log.info("Attempting to map TransactionCreateDto: '{}' to Transaction.", request.transactionId());
        Transaction transaction = transactionDepositDtoToTransactionMapper.map(request, user);

        transactionService.deposit(transaction);
    }

    @Override
    public void withdrawWallet(String telegramId, TransactionWithdrawDto request) {
        User user = userService.findByTelegramId(telegramId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(USER_NOT_FOUND_BY_TG_ID, telegramId)
                ));

        log.info("Attempting to map TransactionCreateDto to Transaction for user with Telegram ID: {}", telegramId);
        Transaction transaction = transactionWithdrawDtoToTransactionMapper.map(request, user);

        transactionService.withdraw(transaction);
    }
}
