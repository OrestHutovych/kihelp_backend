package org.example.kihelp_back.invite.usecase.impl;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.mapper.InviteCreateDtoToInviteMapper;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.invite.service.InviteService;
import org.example.kihelp_back.invite.usecase.InviteCreateUseCase;
import org.example.kihelp_back.user.exception.UserExistException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.example.kihelp_back.invite.util.ErrorMessage.USER_ALREADY_REGISTERED;

@Component
public class InviteCreateUseCaseFacade implements InviteCreateUseCase {
    private final InviteService inviteService;
    private final InviteCreateDtoToInviteMapper inviteCreateDtoToInviteMapper;
    private final UserService userService;
    private final UserCreateUseCase userCreateUseCase;

    public InviteCreateUseCaseFacade(InviteService inviteService,
                                     InviteCreateDtoToInviteMapper inviteCreateDtoToInviteMapper,
                                     UserService userService,
                                     UserCreateUseCase userCreateUseCase) {
        this.inviteService = inviteService;
        this.inviteCreateDtoToInviteMapper = inviteCreateDtoToInviteMapper;
        this.userService = userService;
        this.userCreateUseCase = userCreateUseCase;
    }

    @Override
    public void createInvite(InviteCreateDto inviteCreateDto) {
        User inviter = userService.findByTelegramId(inviteCreateDto.inviterTelegramId());
        boolean inviteeExist = userService.existByTelegramId(inviteCreateDto.inviteeTelegramId());

        if (inviteeExist) {
            throw new UserExistException(String.format(USER_ALREADY_REGISTERED, inviteCreateDto.inviteeTelegramId()));
        }

        Map<String, String> userCreateDto = new HashMap<>();
        userCreateDto.put("id", inviteCreateDto.inviteeTelegramId());
        userCreateDto.put("username", "");

        User invitee = userCreateUseCase.create(userCreateDto);
        Invite invite = inviteCreateDtoToInviteMapper.map(inviteCreateDto, inviter, invitee);

        inviteService.create(invite);
    }
}
