package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.mapper.TransactionWithdrawMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionWithdrawMapperImpl implements TransactionWithdrawMapper {

    @Override
    public Transaction toData(TransactionWithdrawDto dto) {
        if(dto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setInitials("N/A");
        transaction.setAmount(dto.amount());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.IN_PROGRESS);
        transaction.setUser(dto.user());

        return transaction;
    }
}
