package org.example.kihelp_back.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.transaction.exception.TransactionStatusNotFoundException;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.repository.TransactionRepository;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.transaction.util.ErrorMessage.*;

@Service
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
    }

    @Transactional
    public Transaction deposit(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        log.info("Attempting to update balance in wallet for user with telegram id: {}", transaction.getUser().getTelegramId());
        walletService.depositAmountToWalletByUserTelegramId(transaction.getUser().getTelegramId(),
                transaction.getAmount());
        transaction.setStatus(TransactionStatus.COMPLETED);

        log.info("Successfully created transaction and update balance for user with telegram id: {}", transaction.getUser().getTelegramId());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public void withdraw(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        log.info("Attempting to update balance in wallet for user with telegram id: {}", transaction.getUser().getTelegramId());
        walletService.withdrawAmountFromWalletByUserTelegramId(transaction.getUser().getTelegramId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.IN_PROGRESS);

        log.info("Successfully created transaction and update balance for user with telegram id: {}", transaction.getUser().getTelegramId());
        transactionRepository.save(transaction);
    }

    @Transactional
    public void toggleWithdrawStatus(String transactionId, String transactionStatus) {
        Transaction transaction = findTransactionByTransactionId(transactionId);

        transaction.setStatus(resolveTransactionStatus(transactionStatus));

        transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionsByUserTelegramId(String telegramId) {
        log.info("Attempting to get all transaction by user telegram id: {}", telegramId);
        return transactionRepository.findAllByUserTelegramId(telegramId);
    }

    public Transaction findTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException
                        (String.format(TRANSACTION_NOT_FOUND, transactionId))
                );
    }

    private TransactionStatus resolveTransactionStatus(String status) {
        try {
            return TransactionStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new TransactionStatusNotFoundException(String.format(TRANSACTION_STATUS_NOT_FOUND, status));
        }
    }
}
