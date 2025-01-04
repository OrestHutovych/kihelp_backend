package org.example.kihelp_back.user.dto;

import java.time.Instant;

public record UserDto(
        String username,
        String telegramId,
        Instant createdAt
) {
}
