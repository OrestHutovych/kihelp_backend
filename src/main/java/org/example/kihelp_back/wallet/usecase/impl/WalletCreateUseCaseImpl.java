package org.example.kihelp_back.wallet.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.dto.WalletRequest;
import org.example.kihelp_back.wallet.mapper.WalletRequestToWalletMapper;

import org.example.kihelp_back.wallet.service.WalletService;
import org.example.kihelp_back.wallet.usecase.WalletCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WalletCreateUseCaseImpl implements WalletCreateUseCase {
    private final WalletService walletService;
    private final WalletRequestToWalletMapper walletRequestToWalletMapper;
    private final UserService userService;

    public WalletCreateUseCaseImpl(WalletService walletService,
                                   WalletRequestToWalletMapper walletRequestToWalletMapper,
                                   UserService userService) {
        this.walletService = walletService;
        this.walletRequestToWalletMapper = walletRequestToWalletMapper;
        this.userService = userService;
    }

    @Override
    public void createWallet(WalletRequest request) {
        log.info("Attempting to create a wallet for user with ID: {}", request.userId());

        var user = userService.findById(request.userId());
        var wallet = walletRequestToWalletMapper.map(request, user);
        log.info("Mapped wallet request to wallet: {}", wallet);

        walletService.save(wallet);
        log.info("Wallet created for user with ID: {}", wallet.getUser().getId());
    }
}
