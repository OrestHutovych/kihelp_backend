package org.example.kihelp_back.transaction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

import static org.example.kihelp_back.transaction.util.ErrorMessage.TRANSACTION_AMOUNT_MIN;
import static org.example.kihelp_back.transaction.util.ErrorMessage.TRANSACTION_ID_NOT_BLANK;

public record TransactionCreateDto(
        @NotBlank(message =  TRANSACTION_ID_NOT_BLANK)
        String transactionId,
        String initials,
        @Min(value = 10, message = TRANSACTION_AMOUNT_MIN)
        BigDecimal amount,
        String telegramId
) {
}