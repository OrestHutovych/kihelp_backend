package org.example.kihelp_back.wallet.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.dto.WalletResponseDto;
import org.example.kihelp_back.wallet.mapper.WalletToWalletResponseDtoMapper;
import org.example.kihelp_back.wallet.model.Wallet;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WalletGetUseCaseFacade implements WalletGetUseCase {
    private final WalletService walletService;
    private final WalletToWalletResponseDtoMapper walletToWalletResponseMapper;
    private final UserService userService;

    public WalletGetUseCaseFacade(WalletService walletService,
                                  WalletToWalletResponseDtoMapper walletToWalletResponseMapper,
                                  UserService userService) {
        this.walletService = walletService;
        this.walletToWalletResponseMapper = walletToWalletResponseMapper;
        this.userService = userService;
    }

    @Override
    public List<WalletResponseDto> getWalletsByUserTelegramId() {
        User targetUser = userService.findByJwt();
        List<Wallet> wallets = walletService.findByUserTelegramId(targetUser.getTelegramId());

        log.info("Mapping Wallet(s) {} for user with Telegram ID: {} to WalletResponseDto.", wallets.size(), targetUser.getTelegramId());
        List<WalletResponseDto> responseWallets = wallets.stream()
                .map(walletToWalletResponseMapper::map)
                .toList();

        log.info("Successfully mapped WalletsResponseDto(s) {} and returned.", responseWallets.size());
        return responseWallets;
    }
}
