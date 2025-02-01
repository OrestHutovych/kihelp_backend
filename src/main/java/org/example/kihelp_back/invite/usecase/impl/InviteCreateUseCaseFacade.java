package org.example.kihelp_back.invite.usecase.impl;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.mapper.InviteMapper;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.invite.service.InviteService;
import org.example.kihelp_back.invite.usecase.InviteCreateUseCase;
import org.springframework.stereotype.Component;


@Component
public class InviteCreateUseCaseFacade implements InviteCreateUseCase {
    private final InviteService inviteService;
    private final InviteMapper inviteMapper;

    public InviteCreateUseCaseFacade(InviteService inviteService,
                                     InviteMapper inviteMapper) {
        this.inviteService = inviteService;
        this.inviteMapper = inviteMapper;
    }

    @Override
    public void createInvite(InviteCreateDto inviteCreateDto) {
        Invite invite = inviteMapper.toEntity(inviteCreateDto);
        inviteService.create(invite);
    }
}
