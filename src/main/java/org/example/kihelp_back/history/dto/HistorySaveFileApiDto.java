package org.example.kihelp_back.history.dto;

import java.util.List;

public record HistorySaveFileApiDto(
        String telegramId,
        String subjectTitle,
        String teacherName,
        String taskTitle,
        List<String> args,
        String encodedFile,
        String fileName
) {
}
