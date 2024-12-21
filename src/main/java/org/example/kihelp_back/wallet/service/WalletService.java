package org.example.kihelp_back.wallet.service;

import jakarta.validation.constraints.Positive;
import org.example.kihelp_back.wallet.model.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    void save(Wallet wallet);
    void validateDefaultWallet(Wallet wallet);
    Wallet findById(Long id);
    List<Wallet> findByUserTelegramId(String telegramId);
    void updateDefaultWalletBalanceByUserTelegramId(String telegramId, @Positive BigDecimal amount);
    void deleteByNotDefaultByUser(Long userId);
}
