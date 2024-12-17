package org.example.kihelp_back.wallet.usecase;

import org.example.kihelp_back.wallet.dto.WalletResponse;

import java.util.List;

public interface WalletGetUseCase {
    List<WalletResponse> getWalletsByUser(String telegramId);
}
