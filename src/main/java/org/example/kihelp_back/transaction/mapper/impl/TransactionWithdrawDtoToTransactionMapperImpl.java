package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionWithdrawDtoToTransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.user.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionWithdrawDtoToTransactionMapperImpl implements TransactionWithdrawDtoToTransactionMapper {

    @Override
    public Transaction map(TransactionWithdrawDto transactionWithdrawDto, User user) {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setAmount(transactionWithdrawDto.amount());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setInitials(user.getUsername());
        transaction.setUser(user);

        return transaction;
    }
}
