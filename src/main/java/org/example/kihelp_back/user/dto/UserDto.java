package org.example.kihelp_back.user.dto;

public record UserDto(
        String username,
        String telegramId,
        String photo,
        Integer courseNumber,
        String createdAt
) {
}
