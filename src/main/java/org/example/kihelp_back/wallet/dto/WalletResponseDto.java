package org.example.kihelp_back.wallet.dto;

import java.math.BigDecimal;

public record WalletResponseDto(
        String name,
        BigDecimal balance,
        boolean isDefault
) {
}
