package org.example.kihelp_back.wallet.controller;

import org.example.kihelp_back.wallet.dto.WalletRequest;
import org.example.kihelp_back.wallet.usecase.WalletCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletCreateUseCase walletCreateUseCase;

    public WalletController(WalletCreateUseCase walletCreateUseCase) {
        this.walletCreateUseCase = walletCreateUseCase;
    }

    @PostMapping("/wallet")
    public void createWallet(@RequestBody WalletRequest request) {
        walletCreateUseCase.createWallet(request);
    }
}
