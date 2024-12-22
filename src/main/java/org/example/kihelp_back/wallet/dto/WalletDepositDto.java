package org.example.kihelp_back.wallet.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WalletDepositDto(
        @Positive
        BigDecimal amount
) {
}
