package org.example.kihelp_back.user.dto;

public record UserResponse(
        Long id,
        String telegramId,
        String username
) {
}
