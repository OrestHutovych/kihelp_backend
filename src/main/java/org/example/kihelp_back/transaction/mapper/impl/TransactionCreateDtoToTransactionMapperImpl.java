package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.mapper.TransactionCreateDtoToTransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreateDtoToTransactionMapperImpl implements TransactionCreateDtoToTransactionMapper {

    @Override
    public Transaction map(TransactionCreateDto transactionCreateDto, User user) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionCreateDto.transactionId());
        transaction.setInitials(transactionCreateDto.initials());
        transaction.setAmount(transactionCreateDto.amount());
        transaction.setUser(user);

        return transaction;
    }
}
