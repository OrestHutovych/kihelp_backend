package org.example.kihelp_back.transaction.mapper.impl;

import org.example.kihelp_back.transaction.dto.TransactionDto;
import org.example.kihelp_back.transaction.mapper.TransactionMapper;
import org.example.kihelp_back.transaction.model.Transaction;
import org.example.kihelp_back.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionDto toDto(Transaction transaction) {
        if(transaction == null) {
            return null;
        }

        UserDto userDto = new UserDto(
                transaction.getUser().getUsername(),
                transaction.getUser().getTelegramId(),
                transaction.getUser().getCreatedAt().toString()
        );

        return new TransactionDto(
          transaction.getTransactionId(),
          transaction.getInitials(),
          transaction.getAmount(),
          transaction.getType(),
          transaction.getCreatedAt().toString(),
          userDto
        );
    }
}
