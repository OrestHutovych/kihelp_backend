package org.example.kihelp_back.invite.dto;

public record InviteCreateDto(
        String inviterTelegramId,
        String inviteeTelegramId
) {
}
