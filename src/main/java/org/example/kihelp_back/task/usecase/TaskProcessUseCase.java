package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskProcessCreateDto;
import org.example.kihelp_back.task.dto.TaskProcessDto;

public interface TaskProcessUseCase {
    TaskProcessDto processTask(Long taskId, TaskProcessCreateDto request);
}
