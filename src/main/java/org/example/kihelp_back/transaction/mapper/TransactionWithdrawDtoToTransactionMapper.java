package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;

public interface TransactionWithdrawDtoToTransactionMapper extends MapperV2<Transaction, TransactionWithdrawDto, User> {
}
