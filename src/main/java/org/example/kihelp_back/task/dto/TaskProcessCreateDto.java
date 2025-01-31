package org.example.kihelp_back.task.dto;

import java.util.List;

public record TaskProcessCreateDto(
        String taskId,
        List<String> arguments
) {
}
