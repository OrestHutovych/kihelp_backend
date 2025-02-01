package org.example.kihelp_back.invite.usecase.impl;

import org.example.kihelp_back.invite.dto.InviteDto;
import org.example.kihelp_back.invite.mapper.InviteMapper;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.invite.service.InviteService;
import org.example.kihelp_back.invite.usecase.InviteGetUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InviteGetUseCaseFacade implements InviteGetUseCase {
    private final InviteService inviteService;
    private final UserService userService;
    private final InviteMapper inviteMapper;

    public InviteGetUseCaseFacade(InviteService inviteService,
                                  UserService userService,
                                  InviteMapper inviteMapper) {
        this.inviteService = inviteService;
        this.userService = userService;
        this.inviteMapper = inviteMapper;
    }

    @Override
    public List<InviteDto> getInvitesByInviter() {
        User targetUser = userService.findByJwt();
        List<Invite> invitesByUser = inviteService.getInvitesByInviterUserId(targetUser.getTelegramId());

        return invitesByUser.stream()
                .map(inviteMapper::toInviteDto)
                .toList();
    }

    @Override
    public BigDecimal getInvitesPrice() {
        User targetUser = userService.findByJwt();
        List<Invite> invitesByUser = inviteService.getInvitesByInviterUserId(targetUser.getTelegramId());

        return invitesByUser.stream().map(Invite::getInviteeAmountSpend).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
