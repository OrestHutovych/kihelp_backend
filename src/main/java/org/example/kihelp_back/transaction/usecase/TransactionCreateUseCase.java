package org.example.kihelp_back.transaction.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionDepositDto;
import org.example.kihelp_back.transaction.dto.TransactionWithdrawDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TransactionCreateUseCase {
    void depositWallet(String telegramId, @Valid TransactionDepositDto request);
    void withdrawWallet(String telegramId, TransactionWithdrawDto request);
}
