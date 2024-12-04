package org.example.kihelp_back.teacher.model;

import jakarta.validation.constraints.NotBlank;

import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_NAME_NOT_BLANK;

public record TeacherUpdateRequest(
        @NotBlank(message = TEACHER_NAME_NOT_BLANK)
        String name
) {
}
