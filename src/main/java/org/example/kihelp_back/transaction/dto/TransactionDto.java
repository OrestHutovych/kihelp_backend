package org.example.kihelp_back.transaction.dto;

import org.example.kihelp_back.transaction.model.TransactionType;

import java.math.BigDecimal;

public record TransactionDto(
        String transactionId,
        String initials,
        BigDecimal amount,
        TransactionType transactionType,
        String createdTimeStamp
){
}
