package org.example.kihelp_back.invite.mapper;

import org.example.kihelp_back.global.mapper.MapperV3;
import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.model.Invite;
import org.example.kihelp_back.user.model.User;

public interface InviteCreateDtoToInviteMapper extends MapperV3<Invite, InviteCreateDto, User, User> {
}
