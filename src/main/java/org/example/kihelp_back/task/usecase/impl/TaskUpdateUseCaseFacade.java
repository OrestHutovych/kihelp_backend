package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.dto.TaskUpdateDto;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateUseCaseFacade implements TaskUpdateUseCase {
    private final TaskService taskService;

    public TaskUpdateUseCaseFacade(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void updateTask(Long taskId, TaskUpdateDto request) {
        taskService.update(taskId, request);
    }
}
