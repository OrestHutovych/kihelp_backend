package org.example.kihelp_back.wallet.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.wallet.dto.WalletDepositDto;
import org.example.kihelp_back.wallet.dto.WalletDto;
import org.example.kihelp_back.wallet.dto.WalletWithdrawDto;
import org.example.kihelp_back.wallet.usecase.WalletGetUseCase;
import org.example.kihelp_back.wallet.usecase.WalletUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final WalletGetUseCase walletGetUseCase;
    private final WalletUpdateUseCase walletUpdateUseCase;

    public WalletController(WalletGetUseCase walletGetUseCase,
                            WalletUpdateUseCase walletUpdateUseCase) {
        this.walletGetUseCase = walletGetUseCase;
        this.walletUpdateUseCase = walletUpdateUseCase;
    }

    @GetMapping("/")
    public List<WalletDto> getWalletsByUser() {
        return walletGetUseCase.getWalletsByUser();
    }

    @PutMapping("/deposit")
    public void deposit(@Valid @RequestBody WalletDepositDto request){
        walletUpdateUseCase.deposit(request);
    }

    @PutMapping("/withdraw")
    public void withdraw(@Valid @RequestBody WalletWithdrawDto request){
        walletUpdateUseCase.withdraw(request);
    }
}
