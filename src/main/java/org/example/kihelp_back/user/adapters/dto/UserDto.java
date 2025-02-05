package org.example.kihelp_back.user.adapters.dto;

public record UserDto(
        String username,
        String telegramId,
        Integer courseNumber,
        String createdAt
) {
}
