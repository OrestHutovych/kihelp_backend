package org.example.kihelp_back.history.dto;

import org.example.kihelp_back.user.dto.UserDto;

public record TaskDeveloperDto(
        String taskTitle,
        String teacherName,
        String subjectName,
        String arguments,
        String createdAt,
        UserDto user
) {
}
