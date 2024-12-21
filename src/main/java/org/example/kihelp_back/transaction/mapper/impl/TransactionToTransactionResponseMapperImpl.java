package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionResponse;
import org.example.kihelp_back.transaction.mapper.TransactionToTransactionResponseMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionResponseMapperImpl implements TransactionToTransactionResponseMapper {
    @Override
    public TransactionResponse map(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionId(),
                transaction.getInitials(),
                transaction.getAmount(),
                transaction.getCreatedTimeStamp(),
                transaction.getUser().getId()
        );
    }
}
