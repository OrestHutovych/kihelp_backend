package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.global.mapper.Mapper;
import org.example.kihelp_back.transaction.dto.TransactionResponse;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionToTransactionResponseMapper extends Mapper<TransactionResponse, Transaction> {
}
