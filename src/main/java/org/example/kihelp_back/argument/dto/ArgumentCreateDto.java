package org.example.kihelp_back.argument.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static org.example.kihelp_back.argument.util.MessageError.ARG_NAME_NOT_VALID;
import static org.example.kihelp_back.argument.util.MessageError.DESCRIPTION_LENGTH_NOT_VALID;

public record ArgumentCreateDto(
        @NotBlank(message = ARG_NAME_NOT_VALID)
        String name,
        @Size(max = 30, message = DESCRIPTION_LENGTH_NOT_VALID)
        String description) {
}
