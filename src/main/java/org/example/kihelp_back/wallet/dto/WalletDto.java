package org.example.kihelp_back.wallet.dto;

import java.math.BigDecimal;

public record WalletDto(
        String name,
        BigDecimal balance,
        boolean isDefault
) {
}
