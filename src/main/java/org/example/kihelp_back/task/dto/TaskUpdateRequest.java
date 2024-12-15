package org.example.kihelp_back.task.dto;

public record TaskUpdateRequest(
        String title,
        String description,
        String identifier,
        Integer price,
        Double discount,
        boolean visible,
        String type,
        boolean autoGenerate,
        Long developerId
) {
}
