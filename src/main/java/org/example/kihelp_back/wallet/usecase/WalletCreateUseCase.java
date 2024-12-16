package org.example.kihelp_back.wallet.usecase;

import org.example.kihelp_back.wallet.dto.WalletRequest;

public interface WalletCreateUseCase {
    void createWallet(WalletRequest request);
}
