package org.example.kihelp_back.user.dto;

public record UserDto(
        String username,
        String telegramId,
        Integer courseNumber,
        String createdAt
) {
}
