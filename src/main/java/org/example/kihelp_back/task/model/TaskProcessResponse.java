package org.example.kihelp_back.task.model;

import java.time.Instant;

public record TaskProcessResponse(
        String fileName,
        String link,
        Instant createdTimeStamp
) {
}
