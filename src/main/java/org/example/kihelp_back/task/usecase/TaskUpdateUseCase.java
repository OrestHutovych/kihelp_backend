package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskUpdateRequest;

public interface TaskUpdateUseCase {
    void updateTask(Integer taskId, TaskUpdateRequest request);
}
