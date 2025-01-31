package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.task.dto.TaskUpdateDto;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateUseCaseFacade implements TaskUpdateUseCase {
    private final TaskService taskService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TaskUpdateUseCaseFacade(TaskService taskService, IdEncoderApiRepository idEncoderApiRepository) {
        this.taskService = taskService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void updateTask(String taskId, TaskUpdateDto request) {
        Long decodedTaskId = idEncoderApiRepository.findEncoderByName("task").decode(taskId).get(0);
        taskService.update(decodedTaskId, request);
    }
}
