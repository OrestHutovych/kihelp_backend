package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionResponseDto;
import org.example.kihelp_back.transaction.mapper.TransactionToTransactionResponseMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionResponseMapperImpl implements TransactionToTransactionResponseMapper {
    @Override
    public TransactionResponseDto map(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getTransactionId(),
                transaction.getInitials(),
                transaction.getAmount(),
                transaction.getCreatedTimeStamp(),
                transaction.getUser().getId()
        );
    }
}
