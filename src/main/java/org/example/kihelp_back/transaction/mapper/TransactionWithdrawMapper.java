package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.model.Transaction;

public interface TransactionWithdrawMapper {
    Transaction toData(TransactionWithdrawDto dto);
}
