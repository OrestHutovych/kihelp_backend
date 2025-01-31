package org.example.kihelp_back.history.dto;

import org.example.kihelp_back.user.dto.UserDto;

public record TaskDeveloperDto(
        Long historyId,
        UserDto user,
        String subjectName,
        String teacherName,
        String taskTitle,
        String arguments,
        String createdAt
) {
}
