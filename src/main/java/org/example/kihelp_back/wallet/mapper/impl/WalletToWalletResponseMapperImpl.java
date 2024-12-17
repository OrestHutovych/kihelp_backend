package org.example.kihelp_back.wallet.mapper.impl;

import org.example.kihelp_back.wallet.dto.WalletResponse;
import org.example.kihelp_back.wallet.mapper.WalletToWalletResponseMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletToWalletResponseMapperImpl implements WalletToWalletResponseMapper {

    @Override
    public WalletResponse map(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getName(),
                wallet.getBalance(),
                wallet.isDefaultWallet()
        );
    }
}
