package org.example.kihelp_back.wallet.dto;

public record WalletResponseDto(
        String name,
        Double balance,
        boolean isDefault
) {
}
