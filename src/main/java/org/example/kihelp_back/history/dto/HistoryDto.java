package org.example.kihelp_back.history.dto;

import java.time.Instant;

public record HistoryDto(
        String nameOfSubject,
        String nameOfTask,
        String link,
        Instant createdTimeStamp
) {
}
