package org.example.kihelp_back.invite.mapper;

import org.example.kihelp_back.invite.dto.InviteCreateDto;
import org.example.kihelp_back.invite.dto.InviteDto;
import org.example.kihelp_back.invite.model.Invite;

public interface InviteMapper {
    Invite toEntity(InviteCreateDto inviteCreateDto);
    InviteDto toInviteDto(Invite invite);
}
