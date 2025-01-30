package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.dto.TaskCreateDto;
import org.example.kihelp_back.task.mapper.TaskMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.task.util.TaskErrorMessage.IDENTIFIER_BLANK_NOT_VALID;

@Component
public class TaskCreateUseCaseFacade implements TaskCreateUseCase {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskCreateUseCaseFacade(TaskService taskService,
                                   TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Override
    public void createTask(TaskCreateDto taskRequest) {
        if (taskRequest.autoGenerate()) {
            String identifier = taskRequest.identifier();

            if (identifier == null || identifier.isBlank()) {
                throw new IllegalArgumentException(IDENTIFIER_BLANK_NOT_VALID);
            }
        }

        Task task = taskMapper.toEntity(taskRequest);

        taskService.create(task);
    }
}
