package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.mapper.TransactionCreateMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreateMapperImpl implements TransactionCreateMapper {
    private final UserService userService;

    public TransactionCreateMapperImpl(UserService userService) {
        this.userService = userService;
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
