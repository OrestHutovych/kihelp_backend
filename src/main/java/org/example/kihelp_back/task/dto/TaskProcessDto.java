package org.example.kihelp_back.task.dto;

import java.time.Instant;

public record TaskProcessDto(
        String fileName,
        String link,
        Instant createdTimeStamp
) {
}
