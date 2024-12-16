package org.example.kihelp_back.wallet.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.wallet.dto.WalletRequest;
import org.example.kihelp_back.wallet.model.Wallet;

public interface WalletRequestToWalletMapper extends MapperV2<Wallet, WalletRequest, User> {
}
