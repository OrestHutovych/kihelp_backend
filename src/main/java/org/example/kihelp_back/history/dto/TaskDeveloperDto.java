package org.example.kihelp_back.history.dto;

import java.time.Instant;

public record TaskDeveloperDto(
        Long historyId,
        String username,
        String telegramId,
        String subjectName,
        String teacherName,
        String taskTitle,
        String arguments,
        Instant createdAt
) {
}
