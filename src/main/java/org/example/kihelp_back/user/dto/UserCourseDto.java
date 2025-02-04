package org.example.kihelp_back.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UserCourseDto(
        @Min(value = 1)
        @Max(value = 4)
        Integer courseNumber
) {
}
