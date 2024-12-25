package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskDeleteUseCaseFacade implements TaskDeleteUseCase {
    private final TaskService taskService;

    public TaskDeleteUseCaseFacade(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskService.delete(taskId);
    }
}
