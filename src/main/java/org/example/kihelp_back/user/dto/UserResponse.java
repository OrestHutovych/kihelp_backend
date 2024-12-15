package org.example.kihelp_back.user.dto;

public record UserResponse(
        Long id,
        Long telegramId,
        String username
) {
}
