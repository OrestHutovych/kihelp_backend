package org.example.kihelp_back.task.dto;

import java.util.List;

public record TaskGenerateCreateDto(
        String telegramId,
        String taskTitle,
        String teacherName,
        String subjectTitle,
        String identifier,
        List<String> args
) {
}
