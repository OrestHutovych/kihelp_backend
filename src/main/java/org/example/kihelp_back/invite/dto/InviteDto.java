package org.example.kihelp_back.invite.dto;

import org.example.kihelp_back.user.dto.UserDto;

import java.math.BigDecimal;

public record InviteDto(
    UserDto user,
    BigDecimal spendBalance
) {
}
