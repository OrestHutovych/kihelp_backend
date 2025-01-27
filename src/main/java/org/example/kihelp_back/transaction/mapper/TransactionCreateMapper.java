package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionCreateMapper {
    Transaction toEntity(TransactionCreateDto dto);
}
