package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.task.mapper.TaskMapper;
import org.example.kihelp_back.task.dto.TaskDto;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TaskGetUseCaseFacade implements TaskGetUseCase {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TaskGetUseCaseFacade(TaskService taskService,
                                TaskMapper taskMapper, IdEncoderApiRepository idEncoderApiRepository) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public List<TaskDto> getTasksByTeacher(String teacherId) {
        Long decodedTeacherId = idEncoderApiRepository.findEncoderByName("teacher").decode(teacherId).get(0);
        List<Task> tasks = taskService.getByTeacher(decodedTeacherId);

        return tasks.stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }
}
