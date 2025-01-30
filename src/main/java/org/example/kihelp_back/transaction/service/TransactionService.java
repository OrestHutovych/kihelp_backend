package org.example.kihelp_back.transaction.service;

import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.transaction.exception.TransactionExistException;
import org.example.kihelp_back.transaction.exception.TransactionNotFoundException;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.transaction.repository.TransactionHistoryRepository;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.example.kihelp_back.transaction.util.TransactionErrorMessage.*;

@Service
public class TransactionService {
    private final TransactionHistoryRepository transactionRepository;
    private final WalletService walletService;
    private final TelegramBotService telegramBotService;

    public TransactionService(TransactionHistoryRepository transactionRepository,
                              WalletService walletService,
                              TelegramBotService telegramBotService) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
        this.telegramBotService = telegramBotService;
    }

    @Transactional
    public Transaction deposit(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(Transaction transaction) {
        boolean existByTransactionId = transactionRepository.existsByTransactionId(transaction.getTransactionId());

        if(existByTransactionId){
            throw new TransactionExistException(
                    String.format(TRANSACTION_EXISTS, transaction.getTransactionId())
            );
        }

        return transactionRepository.save(transaction);
    }

    public void toggleWithdrawStatus(String transactionId) {
        Transaction transaction = findTransactionByTransactionId(transactionId);

        if(!transaction.getType().equals(TransactionType.WITHDRAW)){
            throw new IllegalArgumentException(TRANSACTION_TYPE_IS_NOT_WITHDRAW);
        }

        if(!transaction.getStatus().equals(TransactionStatus.IN_PROGRESS)) {
            throw new IllegalArgumentException(TRANSACTION_STATUS_NOT_IN_PROGRESS);
        }

        transaction.setStatus(TransactionStatus.COMPLETED);

        transactionRepository.save(transaction);
    }

    public Transaction findTransactionByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException
                        (String.format(TRANSACTION_NOT_FOUND, transactionId))
                );
    }

    public Page<Transaction> findCompletedTransactionsByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findAllCompletedTransactionByUserId(userId, pageable);
    }

    public List<Transaction> findAllInProgressWithdrawTransactions(){
        return transactionRepository.findAllInProgressWithdrawTransactions();
    }

    @Scheduled(fixedRateString = "600000")
    @Transactional
    public void changeFailedStatusWithdrawTransaction(){
        List<Transaction> transactions = findAllInProgressWithdrawTransactions();

        transactions.forEach(t -> {
            Instant failedTime = t.getCreatedAt().plus(24, ChronoUnit.HOURS);
            Instant notificationTime = failedTime.minus(2, ChronoUnit.HOURS);

            if (!t.isWarningSent() && Instant.now().isAfter(notificationTime) && Instant.now().isBefore(failedTime)) {
                telegramBotService.warnWithdrawAdminMessage(t);

                t.setWarningSent(true);
                transactionRepository.save(t);
            }

            long daysBetween = Duration.between(t.getCreatedAt(), Instant.now()).toDays();

            if(daysBetween >= 1){
                t.setStatus(TransactionStatus.FAILED);

                Transaction failedTransaction = transactionRepository.save(t);
                walletService.depositAmountToWalletByUserId(t.getUser().getId(), t.getAmount(), false);

                telegramBotService.failedWithdrawTransaction(failedTransaction);
            }
        });
    }
}
