package org.example.kihelp_back.wallet.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.wallet.dto.WalletDepositDto;
import org.example.kihelp_back.wallet.dto.WalletResponseDto;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.example.kihelp_back.wallet.usecase.WalletUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletGetUseCase walletGetUseCase;
    private final WalletUpdateUseCase walletUpdateUseCase;

    public WalletController(WalletGetUseCase walletGetUseCase,
                            WalletUpdateUseCase walletUpdateUseCase) {
        this.walletGetUseCase = walletGetUseCase;
        this.walletUpdateUseCase = walletUpdateUseCase;
    }

    @GetMapping("/getWalletsByUser")
    public List<WalletResponseDto> getWalletsByUserTelegramId() {
        return walletGetUseCase.getWalletsByUserTelegramId();
    }

    @PutMapping("/deposit/{telegram_id}")
    public void deposit(@PathVariable("telegram_id") String telegramId, @Valid @RequestBody WalletDepositDto request){
        walletUpdateUseCase.deposit(telegramId, request);
    }
}
