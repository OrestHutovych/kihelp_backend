package org.example.kihelp_back.task.dto;

import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.user.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;

public record TaskDto(
        String id,
        String title,
        String description,
        String identifier,
        BigDecimal price,
        BigDecimal discount,
        boolean visible,
        String type,
        UserDto developer,
        boolean autoGenerate,
        String createdAt,
        List<ArgumentDto> arguments,
        TeacherDto teacher
) {
}
