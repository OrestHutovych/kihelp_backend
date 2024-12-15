package org.example.kihelp_back.user.dto;

import jakarta.validation.constraints.NotNull;

import static org.example.kihelp_back.user.util.ErrorMessage.TELEGRAM_ID_NOT_NULL;

public record UserRequest(
        @NotNull(message = TELEGRAM_ID_NOT_NULL)
        Long telegramId,
        String username
) {
}
