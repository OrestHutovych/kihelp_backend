package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;

public interface TransactionDepositDtoToTransactionMapper extends MapperV2<Transaction, TransactionDepositDto, User> {
}
