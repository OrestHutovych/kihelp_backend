package org.example.kihelp_back.wallet.usecase;

import org.example.kihelp_back.wallet.dto.WalletResponseDto;

import java.util.List;

public interface WalletGetUseCase {
    List<WalletResponseDto> getWalletsByUserTelegramId();
}
