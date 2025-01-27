package org.example.kihelp_back.wallet.mapper.impl;

import org.example.kihelp_back.wallet.dto.WalletDto;
import org.example.kihelp_back.wallet.mapper.WalletMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapperImpl implements WalletMapper {

    @Override
    public WalletDto toDto(Wallet wallet) {
        if(wallet == null) {
            return null;
        }

        return new WalletDto(
                wallet.getName(),
                wallet.getBalance(),
                wallet.isDefaultWallet()
        );
    }
}
