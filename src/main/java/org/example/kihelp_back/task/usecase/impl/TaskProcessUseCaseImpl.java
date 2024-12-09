package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.model.TaskProcessRequest;
import org.example.kihelp_back.task.model.TaskProcessResponse;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskProcessUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessUseCaseImpl implements TaskProcessUseCase {
    private final TaskService taskService;

    public TaskProcessUseCaseImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskProcessResponse processTask(Integer taskId, TaskProcessRequest request) {
        // todo process
        return null;
    }
}
