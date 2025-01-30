package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskProcessCreateDto;

import java.util.Map;

public interface TaskProcessUseCase {
    Map<String, String> processTask(TaskProcessCreateDto request);
}
