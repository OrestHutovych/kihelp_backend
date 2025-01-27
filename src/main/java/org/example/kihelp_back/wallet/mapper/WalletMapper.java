package org.example.kihelp_back.wallet.mapper;

import org.example.kihelp_back.wallet.dto.WalletDto;
import org.example.kihelp_back.wallet.model.Wallet;

public interface WalletMapper {
    WalletDto toDto(Wallet wallet);
}
