package org.example.kihelp_back.transaction.usecase.impl;

import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.task.model.TaskType;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawStatusDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.service.TransactionService;
import org.example.kihelp_back.transaction.usecase.TransactionUpdateUseCase;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.task.util.TaskErrorMessage.TYPE_NOT_VALID;

@Component
public class TransactionUpdateUseCaseFacade implements TransactionUpdateUseCase {
    private final TransactionService transactionService;
    private final WalletService walletService;
    private final TelegramBotService telegramBotService;

    public TransactionUpdateUseCaseFacade(TransactionService transactionService,
                                          WalletService walletService, TelegramBotService telegramBotService) {
        this.transactionService = transactionService;
        this.walletService = walletService;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void toggleWithdrawStatus(TransactionWithdrawStatusDto request) {
        TransactionStatus status = resolveType(request.status());
        Transaction transaction = transactionService.toggleWithdrawStatus(request.transactionId(), status);

        if(transaction.getStatus().equals(TransactionStatus.CANCELLED)) {
            walletService.depositAmountToWalletByUserId(transaction.getUser().getId(), transaction.getAmount(), false);
            telegramBotService.failedWithdrawTransaction(transaction);
        }else{
            telegramBotService.successWithdrawTransaction(transaction);
        }
    }

    private TransactionStatus resolveType(String type) {
        try {
            return TransactionStatus.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }

}
