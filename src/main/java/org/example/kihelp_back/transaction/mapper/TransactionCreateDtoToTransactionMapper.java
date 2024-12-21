package org.example.kihelp_back.transaction.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.model.User;

public interface TransactionCreateDtoToTransactionMapper extends MapperV2<Transaction, TransactionCreateDto, User> {
}
