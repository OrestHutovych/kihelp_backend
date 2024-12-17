package org.example.kihelp_back.wallet.dto;

public record WalletResponse(
        Long id,
        String name,
        Double balance,
        boolean isDefault
) {
}
