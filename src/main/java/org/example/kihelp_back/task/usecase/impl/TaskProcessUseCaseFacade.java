package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.task.dto.TaskProcessCreateDto;
import org.example.kihelp_back.task.dto.TaskProcessDto;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskProcessUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessUseCaseFacade implements TaskProcessUseCase {
    private final TaskService taskService;
    private final HistoryService historyService;

    public TaskProcessUseCaseFacade(TaskService taskService,
                                    HistoryService historyService) {
        this.taskService = taskService;
        this.historyService = historyService;
    }

    @Override
    public TaskProcessDto processTask(Long taskId, TaskProcessCreateDto request) {
        return null;
    }
}
