package org.example.kihelp_back.wallet.controller;

import org.example.kihelp_back.wallet.dto.WalletRequest;
import org.example.kihelp_back.wallet.dto.WalletResponse;
import org.example.kihelp_back.wallet.usecase.WalletCreateUseCase;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletCreateUseCase walletCreateUseCase;
    private final WalletGetUseCase walletGetUseCase;

    public WalletController(WalletCreateUseCase walletCreateUseCase,
                            WalletGetUseCase walletGetUseCase) {
        this.walletCreateUseCase = walletCreateUseCase;
        this.walletGetUseCase = walletGetUseCase;
    }

    @PostMapping("/wallet")
    public void createWallet(@RequestBody WalletRequest request) {
        walletCreateUseCase.createWallet(request);
    }

    @GetMapping("/wallet/by/user/{telegram_id}")
    public List<WalletResponse> getWalletsByUser(@PathVariable("telegram_id") String telegramId) {
        return walletGetUseCase.getWalletsByUser(telegramId);
    }
}
