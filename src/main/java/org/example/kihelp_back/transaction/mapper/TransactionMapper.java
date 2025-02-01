package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionMapper {
    TransactionDto toTransactionDto(Transaction transaction);
    Transaction toEntity(TransactionWithdrawDto transactionWithdrawDto);
    Transaction toEntity(TransactionCreateDto dto);
}
