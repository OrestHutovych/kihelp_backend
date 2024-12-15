package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.mapper.TaskToTaskResponseMapper;
import org.example.kihelp_back.task.dto.TaskResponse;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TaskGetUseCaseImpl implements TaskGetUseCase {
    private final TaskService taskService;
    private final TaskToTaskResponseMapper taskToTaskResponseMapper;

    public TaskGetUseCaseImpl(TaskService taskService,
                              TaskToTaskResponseMapper taskToTaskResponseMapper) {
        this.taskService = taskService;
        this.taskToTaskResponseMapper = taskToTaskResponseMapper;
    }

    @Override
    public List<TaskResponse> getTasksByTeacher(Integer teacherId) {
        log.debug("Start fetching task for teacher id: {}", teacherId);
        var tasks = taskService.getByTeacher(teacherId);
        log.debug("Fetched {} task(s) for teacher id: {}", tasks.size(), teacherId);

        log.debug("Mapping task(s) {} to TaskResponse DTOs.", tasks.size());
        var tasksResponse = tasks
                .stream()
                .map(taskToTaskResponseMapper::map)
                .toList();
        log.debug("Successfully mapped {} task(s) to TaskResponse DTOs for teacher idr: {}",
                tasksResponse.size(), teacherId
        );

        return tasksResponse;
    }
}
