package org.example.kihelp_back.transaction.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionDepositDtoToTransactionMapper;
import org.example.kihelp_back.transaction.mapper.TransactionWithdrawDtoToTransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionCreateUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionCreateUseCaseFacade implements TransactionCreateUseCase {
    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionDepositDtoToTransactionMapper transactionDepositDtoToTransactionMapper;
    private final TransactionWithdrawDtoToTransactionMapper transactionWithdrawDtoToTransactionMapper;
    private final TelegramBotService telegramBotService;

    public TransactionCreateUseCaseFacade(
            TransactionService transactionService,
            UserService userService,
            TransactionDepositDtoToTransactionMapper transactionDepositDtoToTransactionMapper,
            TransactionWithdrawDtoToTransactionMapper transactionWithdrawDtoToTransactionMapper,
            TelegramBotService telegramBotService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionDepositDtoToTransactionMapper = transactionDepositDtoToTransactionMapper;
        this.transactionWithdrawDtoToTransactionMapper = transactionWithdrawDtoToTransactionMapper;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void depositWallet(String telegramId, TransactionDepositDto request) {
        User user = userService.findByTelegramId(telegramId);

        log.info("Attempting to map TransactionCreateDto: '{}' to Transaction.", request.transactionId());
        Transaction transaction = transactionDepositDtoToTransactionMapper.map(request, user);

        transactionService.deposit(transaction);
    }

    @Override
    public void withdrawWallet(String telegramId, TransactionWithdrawDto request) {
        User user = userService.findByTelegramId(telegramId);

        log.info("Attempting to map TransactionCreateDto to Transaction for user with Telegram ID: {}", telegramId);
        Transaction transaction = transactionWithdrawDtoToTransactionMapper.map(request, user);

        transactionService.withdraw(transaction);

        log.info("Sending message about withdraw transaction: {} to admin(s)", transaction.getTransactionId());
        telegramBotService.withdrawAdminMessage(transaction, request.cardNumber());
    }
}
