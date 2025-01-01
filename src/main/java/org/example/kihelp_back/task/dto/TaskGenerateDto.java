package org.example.kihelp_back.task.dto;

import java.time.Instant;

public record TaskGenerateDto(
        String fileName,
        String link,
        Instant createdAt
) {
}
