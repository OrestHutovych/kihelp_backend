package org.example.kihelp_back.wallet.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.wallet.dto.WalletResponse;
import org.example.kihelp_back.wallet.mapper.WalletToWalletResponseMapper;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class WalletGetUseCaseImpl implements WalletGetUseCase {
    private final WalletService walletService;
    private final WalletToWalletResponseMapper walletToWalletResponseMapper;

    public WalletGetUseCaseImpl(WalletService walletService,
                                WalletToWalletResponseMapper walletToWalletResponseMapper) {
        this.walletService = walletService;
        this.walletToWalletResponseMapper = walletToWalletResponseMapper;
    }

    @Override
    public List<WalletResponse> getWalletsByUser(String telegramId) {
        var wallets = walletService.findByUserTelegramId(telegramId);

        log.info("Attempting to map wallets {} to wallet response", wallets.size());
        return wallets
                .stream()
                .map(walletToWalletResponseMapper::map)
                .toList();
    }
}
