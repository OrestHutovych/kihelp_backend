package org.example.kihelp_back.user.model;

public record UserRequest(
        Long telegramId,
        String username
) {
}
