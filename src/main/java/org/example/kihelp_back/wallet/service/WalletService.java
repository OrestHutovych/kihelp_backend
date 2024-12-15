package org.example.kihelp_back.wallet.service;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.model.Wallet;

public interface WalletService {
    Wallet save(Wallet wallet);
    Wallet createDefaultWallet(User user);
}
