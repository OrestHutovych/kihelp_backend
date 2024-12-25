package org.example.kihelp_back.wallet.usecase.impl;

import org.example.kihelp_back.wallet.dto.WalletDepositDto;
import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class WalletUpdateUseCaseFacade implements WalletUpdateUseCase {
    private final WalletService walletService;

    public WalletUpdateUseCaseFacade(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void deposit(String telegramId, WalletDepositDto request) {
        walletService.depositAmountToWalletByUserTelegramId(telegramId, request.amount());
    }
}
