package org.example.kihelp_back.transaction.service;

import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static org.example.kihelp_back.transaction.util.TransactionErrorMessage.*;

@Slf4j
@Service
public class TransactionService {
    private final TransactionHistoryRepository transactionRepository;
    private final WalletService walletService;

    public TransactionService(TransactionHistoryRepository transactionRepository,
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

    //    @Scheduled(fixedRateString = "3600000")
    @Scheduled(fixedRateString = "60000")
    @Transactional
    public void changeFailedStatusWithdrawTransaction(){
        List<Transaction> transactions = findAllInProgressWithdrawTransactions();

        transactions.forEach(t -> {
            long daysBetween = Duration.between(t.getCreatedAt(), Instant.now()).toDays();

            if(daysBetween >= 1){
                t.setStatus(TransactionStatus.FAILED);

                transactionRepository.save(t);
                walletService.depositAmountToDevWalletByUserId(t.getUser().getId(), t.getAmount());
            }

            // todo send message to admin(s) chat before 2 hours to failed withdraw transaction
            // todo send message to user chat if transaction changed status to failed
        });
    }
}
