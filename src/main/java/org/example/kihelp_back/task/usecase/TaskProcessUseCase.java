package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskProcessRequest;
import org.example.kihelp_back.task.dto.TaskProcessResponse;

public interface TaskProcessUseCase {
    TaskProcessResponse processTask(Integer taskId, TaskProcessRequest request);
}
