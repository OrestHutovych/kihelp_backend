package org.example.kihelp_back.task.dto;

import java.util.List;

public record TaskProcessRequest(
        List<String> arguments
) {
}
