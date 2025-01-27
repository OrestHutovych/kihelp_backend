package org.example.kihelp_back.transaction.dto;

import org.example.kihelp_back.user.model.User;

import java.math.BigDecimal;

public record TransactionWithdrawDto(
        BigDecimal amount,
        User user
) {
}
