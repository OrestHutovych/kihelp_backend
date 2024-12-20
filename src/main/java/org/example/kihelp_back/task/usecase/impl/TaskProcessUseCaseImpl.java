package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.task.dto.TaskProcessRequest;
import org.example.kihelp_back.task.dto.TaskProcessResponse;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskProcessUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessUseCaseImpl implements TaskProcessUseCase {
    private final TaskService taskService;
    private final HistoryService historyService;

    public TaskProcessUseCaseImpl(TaskService taskService,
                                  HistoryService historyService) {
        this.taskService = taskService;
        this.historyService = historyService;
    }

    @Override
    public TaskProcessResponse processTask(Integer taskId, TaskProcessRequest request) {
        var taskProcessResponse = taskService.process(taskId, request);

        return taskProcessResponse;
    }
}
