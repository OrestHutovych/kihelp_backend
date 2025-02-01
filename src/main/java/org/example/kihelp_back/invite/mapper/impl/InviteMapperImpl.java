package org.example.kihelp_back.invite.mapper.impl;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.dto.InviteDto;
import org.example.kihelp_back.invite.mapper.InviteMapper;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.user.dto.UserDto;
import org.example.kihelp_back.user.exception.UserExistException;
import org.example.kihelp_back.user.mapper.UserMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.example.kihelp_back.invite.util.ErrorMessage.USER_ALREADY_REGISTERED;

@Component
public class InviteMapperImpl implements InviteMapper {
    private final UserService userService;
    private final UserMapper userMapper;

    public InviteMapperImpl(UserService userService,
                            UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public Invite toEntity(InviteCreateDto inviteCreateDto) {
        if(inviteCreateDto == null) {
            return null;
        }

        Invite invite = new Invite();

        invite.setInviterUser(findInviter(inviteCreateDto.inviterTelegramId()));
        invite.setInviteeUser(createInvitee(inviteCreateDto.inviteeTelegramId()));

        return invite;
    }

    @Override
    public InviteDto toInviteDto(Invite invite) {
        if(invite == null) {
            return null;
        }

        UserDto user = new UserDto(
          invite.getInviteeUser().getUsername(),
          invite.getInviteeUser().getTelegramId(),
          invite.getInviteeUser().getCreatedAt().toString()
        );

        return new InviteDto(
                user,
                invite.getInviteeAmountSpend()
        );
    }

    private User findInviter(String telegramId){
        return userService.findByTelegramId(telegramId);
    }

    private User createInvitee(String telegramId) {
        Map<String, String> initUserData = new HashMap<>();
        initUserData.put("id", telegramId);
        initUserData.put("username", "");

        if(userService.existByTelegramId(telegramId)) {
            throw new UserExistException(String.format(USER_ALREADY_REGISTERED, telegramId));
        }

        User mappedUser =  userMapper.toEntity(initUserData);
        return userService.save(mappedUser);
    }
}
