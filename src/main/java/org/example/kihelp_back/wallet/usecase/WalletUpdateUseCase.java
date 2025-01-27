package org.example.kihelp_back.wallet.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.wallet.dto.WalletDepositDto;
import org.example.kihelp_back.wallet.dto.WalletWithdrawDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface WalletUpdateUseCase {
    void deposit(@Valid WalletDepositDto request);
    void withdraw(@Valid WalletWithdrawDto request);
}
