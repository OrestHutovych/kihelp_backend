package org.example.kihelp_back.user.model;

import jakarta.validation.constraints.NotNull;

import static org.example.kihelp_back.user.util.ErrorMessage.USER_BAN_VALUE_NOT_NULL;

public record UserBanRequest(
        @NotNull(message = USER_BAN_VALUE_NOT_NULL)
        boolean value
) {
}
