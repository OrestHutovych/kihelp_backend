package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
}
