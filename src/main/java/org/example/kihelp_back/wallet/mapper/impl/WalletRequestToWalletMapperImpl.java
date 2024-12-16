package org.example.kihelp_back.wallet.mapper.impl;

import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.dto.WalletRequest;
import org.example.kihelp_back.wallet.mapper.WalletRequestToWalletMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletRequestToWalletMapperImpl implements WalletRequestToWalletMapper {

    @Override
    public Wallet map(WalletRequest walletRequest, User user) {
        return Wallet.builder()
                .balance(0.0)
                .name(walletRequest.name())
                .defaultWallet(false)
                .user(user)
                .build();
    }
}
