package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.mapper.TransactionDepositDtoToTransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.example.kihelp_back.user.model.User;
import org.springframework.stereotype.Component;


@Component
public class TransactionDepositDtoToTransactionMapperImpl implements TransactionDepositDtoToTransactionMapper {

    @Override
    public Transaction map(TransactionDepositDto transactionCreateDto, User user) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionCreateDto.transactionId());
        transaction.setInitials(transactionCreateDto.initials());
        transaction.setAmount(transactionCreateDto.amount());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setUser(user);

        return transaction;
    }
}
