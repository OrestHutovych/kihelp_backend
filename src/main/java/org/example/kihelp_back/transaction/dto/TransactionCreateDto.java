package org.example.kihelp_back.transaction.dto;

import jakarta.validation.constraints.Positive;
import org.example.kihelp_back.transaction.model.TransactionStatus;
import org.example.kihelp_back.transaction.model.TransactionType;

import java.math.BigDecimal;

public record TransactionCreateDto(String transactionId,
                                   String initials,
                                   @Positive BigDecimal amount,
                                   TransactionType type,
                                   TransactionStatus status,
                                   String userTelegramId) {
}