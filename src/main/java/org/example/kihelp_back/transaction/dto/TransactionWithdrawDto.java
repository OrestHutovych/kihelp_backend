package org.example.kihelp_back.transaction.dto;

import java.math.BigDecimal;

public record TransactionWithdrawDto(
        Long cardNumber,
        BigDecimal amount
) {
}
