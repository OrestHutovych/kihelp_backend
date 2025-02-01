package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    private final UserService userService;

    public TransactionMapperImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public TransactionDto toTransactionDto(Transaction transaction) {
        if(transaction == null) {
            return null;
        }

        return new TransactionDto(
          transaction.getTransactionId(),
          transaction.getInitials(),
          transaction.getAmount(),
          transaction.getType(),
          transaction.getCreatedAt().toString()
        );
    }

    @Override
    public Transaction toEntity(TransactionWithdrawDto transactionWithdrawDto) {
        if(transactionWithdrawDto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setInitials("N/A");
        transaction.setAmount(transactionWithdrawDto.amount());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setUser(transactionWithdrawDto.user());

        return transaction;
    }

    @Override
    public Transaction toEntity(TransactionCreateDto dto) {
        if(dto == null) {
            return null;
        }

        Transaction transactionHistory = new Transaction();

        transactionHistory.setTransactionId(dto.transactionId());
        transactionHistory.setInitials(dto.initials());
        transactionHistory.setAmount(dto.amount());
        transactionHistory.setType(dto.type());
        transactionHistory.setStatus(dto.status());
        transactionHistory.setUser(findUser(dto.userTelegramId()));

        return transactionHistory;
    }

    private User findUser(String userTelegramId) {
        return userService.findByTelegramId(userTelegramId);
    }
}
