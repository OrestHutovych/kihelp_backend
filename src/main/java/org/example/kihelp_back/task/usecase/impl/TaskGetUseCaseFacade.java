package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
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

    public TaskGetUseCaseFacade(TaskService taskService,
                                TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskDto> getTasksByTeacher(Long teacherId) {
        List<Task> tasks = taskService.getByTeacher(teacherId);

        return tasks.stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }
}
