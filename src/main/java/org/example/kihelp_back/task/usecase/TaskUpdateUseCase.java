package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskUpdateDto;

public interface TaskUpdateUseCase {
    void updateTask(String taskId, TaskUpdateDto request);
}
