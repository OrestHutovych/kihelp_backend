package org.example.kihelp_back.wallet.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.dto.WalletDto;
import org.example.kihelp_back.wallet.mapper.WalletMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WalletGetUseCaseFacade implements WalletGetUseCase {
    private final WalletService walletService;
    private final WalletMapper walletMapper;
    private final UserService userService;

    public WalletGetUseCaseFacade(WalletService walletService,
                                  WalletMapper walletMapper,
                                  UserService userService) {
        this.walletService = walletService;
        this.walletMapper = walletMapper;
        this.userService = userService;
    }

    @Override
    public List<WalletDto> getWalletsByUser() {
        User targetUser = userService.findByJwt();
        List<Wallet> wallets = walletService.findByUserId(targetUser.getId());

        return wallets.stream()
                .map(walletMapper::toDto)
                .toList();
    }
}
