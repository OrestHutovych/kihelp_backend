package org.example.kihelp_back.wallet.service.impl;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.exception.WalletDefaultExistException;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.repository.WalletRepository;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import static org.example.kihelp_back.wallet.util.ErrorMessage.DEFAULT_WALLET_EXIST;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        if (wallet.isDefaultWallet()){
            throw new WalletDefaultExistException(DEFAULT_WALLET_EXIST);
        }

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet createDefaultWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setDefaultWallet(true);
        wallet.setUser(user);

        return wallet;
    }
}
