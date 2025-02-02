package org.example.kihelp_back.teacher.dto;

import jakarta.validation.constraints.NotBlank;

import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_NAME_NOT_BLANK;

public record TeacherCreateDto(
        @NotBlank(message = TEACHER_NAME_NOT_BLANK)
        String name,
        String subjectId
) {
}
