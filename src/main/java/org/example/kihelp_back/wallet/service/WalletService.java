package org.example.kihelp_back.wallet.service;

import org.example.kihelp_back.wallet.model.Wallet;

public interface WalletService {
    void save(Wallet wallet);
    void validateDefaultWallet(Wallet wallet);
}
