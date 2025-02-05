package org.example.kihelp_back.subject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import static org.example.kihelp_back.subject.util.SubjectErrorMessages.COURSE_VALUE;
import static org.example.kihelp_back.subject.util.SubjectErrorMessages.SUBJECT_NAME_NOT_VALID;

public record SubjectCreateDto(
        @NotBlank(message = SUBJECT_NAME_NOT_VALID)
        String name,
        @Min(value = 1, message = COURSE_VALUE)
        @Max(value = 4, message = COURSE_VALUE)
        Integer courseNumber
) {
}