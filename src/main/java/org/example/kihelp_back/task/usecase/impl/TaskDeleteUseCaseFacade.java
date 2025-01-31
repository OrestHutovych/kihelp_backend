package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class TaskDeleteUseCaseFacade implements TaskDeleteUseCase {
    private final TaskService taskService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TaskDeleteUseCaseFacade(TaskService taskService, IdEncoderApiRepository idEncoderApiRepository) {
        this.taskService = taskService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void deleteTask(String taskId) {
        Long decodeTaskId = idEncoderApiRepository.findEncoderByName("task").decode(taskId).get(0);
        taskService.delete(decodeTaskId);
    }
}
