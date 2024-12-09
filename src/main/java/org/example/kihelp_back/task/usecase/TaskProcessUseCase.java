package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.model.TaskProcessRequest;
import org.example.kihelp_back.task.model.TaskProcessResponse;

public interface TaskProcessUseCase {
    TaskProcessResponse processTask(Integer taskId, TaskProcessRequest request);
}
