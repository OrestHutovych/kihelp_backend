package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.mapper.TaskToTaskDtoMapper;
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
    private final TaskToTaskDtoMapper taskToTaskDtoMapper;

    public TaskGetUseCaseFacade(TaskService taskService,
                                TaskToTaskDtoMapper taskToTaskDtoMapper) {
        this.taskService = taskService;
        this.taskToTaskDtoMapper = taskToTaskDtoMapper;
    }

    @Override
    public List<TaskDto> getTasksByTeacher(Long teacherId) {
        List<Task> tasks = taskService.getByTeacher(teacherId);

        log.info("Attempting to map Task(s) {} to TaskDto and return it.", tasks.size());
        return tasks.stream()
                .map(taskToTaskDtoMapper::map)
                .toList();
    }
}
