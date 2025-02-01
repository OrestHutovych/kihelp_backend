package org.example.kihelp_back.invite.usecase.impl;

import org.example.kihelp_back.invite.exception.InviteDepositException;
import org.example.kihelp_back.invite.service.InviteService;
import org.example.kihelp_back.invite.usecase.InviteUpdateUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.wallet.service.WalletService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.example.kihelp_back.invite.util.InviteErrorMessage.DEPOSIT_ERR;

@Component
public class InviteUpdateUseCaseFacade implements InviteUpdateUseCase {
    private final InviteService inviteService;
    private final UserService userService;
    private final WalletService walletService;

    public InviteUpdateUseCaseFacade(InviteService inviteService,
                                     UserService userService,
                                     WalletService walletService) {
        this.inviteService = inviteService;
        this.userService = userService;
        this.walletService = walletService;
    }

    @Override
    @Transactional
    public void depositInviteAmount() {
        User targetUser = userService.findByJwt();
        boolean valid = inviteService.depositToWallet(targetUser.getTelegramId());

        if (valid) {
            walletService.depositAmountToWalletByUserId(targetUser.getId(), BigDecimal.valueOf(100.0), true);
        }else {
            throw new InviteDepositException(DEPOSIT_ERR);
        }
    }
}
