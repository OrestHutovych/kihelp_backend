package org.example.kihelp_back.task.model;

import org.example.kihelp_back.argument.model.Argument;

import java.time.Instant;
import java.util.List;

public record TaskResponse(
        Integer id,
        String title,
        String description,
        String identifier,
        Integer price,
        Double discount,
        boolean visible,
        String type,
        String developer,
        boolean autoGenerate,
        Instant createdTimeStamp,
        List<Argument> arguments,
        Integer teacherId
) {
}
