package org.example.kihelp_back.task.model;

import java.util.List;

public record TaskProcessRequest(
        List<String> arguments
) {
}
