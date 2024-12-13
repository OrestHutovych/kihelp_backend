package org.example.kihelp_back.user.model;

public record UserResponse(
        Long id,
        Long telegramId,
        String username
) {
}
