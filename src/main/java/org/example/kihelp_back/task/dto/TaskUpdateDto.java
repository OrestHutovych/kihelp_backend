package org.example.kihelp_back.task.dto;

import java.math.BigDecimal;

public record TaskUpdateDto(
        String title,
        String description,
        String identifier,
        BigDecimal price,
        boolean visible,
        String type,
        boolean autoGenerate,
        String developerTelegramId
) {
}
