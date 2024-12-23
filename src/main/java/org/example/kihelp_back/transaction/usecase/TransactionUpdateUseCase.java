package org.example.kihelp_back.transaction.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TransactionUpdateUseCase {
    void depositWallet(@Valid TransactionDepositDto request);
}
