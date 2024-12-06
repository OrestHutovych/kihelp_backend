package org.example.kihelp_back.task.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.*;

public record TaskRequest(
        @NotBlank(message = TITLE_BLANK_NOT_VALID)
        String title,
        String description,
        @NotBlank(message = PATH_BLANK_NOT_VALID)
        String path,
        @Min(value = 1, message = PRICE_MIN_NOT_VALID)
        Integer price,
        String type,
        Integer teacherId,
        List<Integer> args
) {
}
