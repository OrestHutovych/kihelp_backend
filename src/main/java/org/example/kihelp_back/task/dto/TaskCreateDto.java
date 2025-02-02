package org.example.kihelp_back.task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

import static org.example.kihelp_back.task.util.TaskErrorMessage.*;

public record TaskCreateDto(
        @NotBlank(message = TITLE_BLANK_NOT_VALID)
        String title,
        String description,
        String identifier,
        @Min(value = 1, message = PRICE_MIN_NOT_VALID)
        BigDecimal price,
        String type,
        String developerTelegramId,
        boolean autoGenerate,
        String teacherId,
        List<String> args
) {
}
