package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskDeleteUseCaseImpl implements TaskDeleteUseCase {
    private final TaskService taskService;

    public TaskDeleteUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskService.delete(taskId);
    }
}
