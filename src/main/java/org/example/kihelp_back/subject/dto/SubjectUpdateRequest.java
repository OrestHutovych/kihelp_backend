package org.example.kihelp_back.subject.dto;

import jakarta.validation.constraints.NotBlank;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NAME_NOT_VALID;

public record SubjectUpdateRequest(
        @NotBlank(message = SUBJECT_NAME_NOT_VALID)
        String name
) {
}
