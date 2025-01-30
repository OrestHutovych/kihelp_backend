package org.example.kihelp_back.task.dto;

import java.util.List;

public record TaskProcessCreateDto(
        Long taskId,
        List<String> arguments
) {
}
