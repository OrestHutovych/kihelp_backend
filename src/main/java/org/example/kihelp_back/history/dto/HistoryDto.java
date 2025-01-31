package org.example.kihelp_back.history.dto;


public record HistoryDto(
        String subjectName,
        String taskTitle,
        String link,
        String createdAt
) {
}
