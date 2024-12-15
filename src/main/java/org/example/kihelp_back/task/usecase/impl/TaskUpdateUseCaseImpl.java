package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.dto.TaskUpdateRequest;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateUseCaseImpl implements TaskUpdateUseCase {
    private final TaskService taskService;

    public TaskUpdateUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void updateTask(Integer taskId, TaskUpdateRequest request) {
        taskService.update(taskId, request);
    }
}
