package org.example.kihelp_back.wallet.mapper.impl;

import org.example.kihelp_back.wallet.dto.WalletResponseDto;
import org.example.kihelp_back.wallet.mapper.WalletToWalletResponseDtoMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletToWalletResponseDtoMapperImpl implements WalletToWalletResponseDtoMapper {

    @Override
    public WalletResponseDto map(Wallet wallet) {
        return new WalletResponseDto(
                wallet.getName(),
                wallet.getBalance(),
                wallet.isDefaultWallet()
        );
    }
}
