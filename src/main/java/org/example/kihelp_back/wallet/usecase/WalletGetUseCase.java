package org.example.kihelp_back.wallet.usecase;

import org.example.kihelp_back.wallet.dto.JarDto;
import org.example.kihelp_back.wallet.dto.WalletDto;

import java.util.List;

public interface WalletGetUseCase {
    List<WalletDto> getWalletsByUser(String telegramId);
    JarDto getJarForDeposit();
}
