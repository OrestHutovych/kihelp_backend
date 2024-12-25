package org.example.kihelp_back.task.dto;

import org.example.kihelp_back.argument.model.Argument;

import java.time.Instant;
import java.util.List;

public record TaskDto(
        Long id,
        String title,
        String description,
        String identifier,
        Integer price,
        Double discount,
        boolean visible,
        String type,
        Long developerId,
        boolean autoGenerate,
        Instant createdAt,
        List<Argument> arguments,
        Long teacherId
) {
}
