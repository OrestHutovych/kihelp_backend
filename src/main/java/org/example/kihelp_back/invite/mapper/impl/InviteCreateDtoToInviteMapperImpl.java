package org.example.kihelp_back.invite.mapper.impl;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.mapper.InviteCreateDtoToInviteMapper;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.user.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InviteCreateDtoToInviteMapperImpl implements InviteCreateDtoToInviteMapper {

    @Override
    public Invite map(InviteCreateDto inviteCreateDto, User user, User user2) {
        Invite invite = new Invite();

        invite.setInviteeAmountSpend(BigDecimal.ZERO);
        invite.setRewardGranted(false);
        invite.setInviterUser(user);
        invite.setInviteeUser(user2);

        return invite;
    }
}
