package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.mapper.TaskToTaskResponseMapper;
import org.example.kihelp_back.task.model.TaskResponse;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        var tasks = taskService.getByTeacher(teacherId);
        var tasksResponse = tasks
                .stream()
                .map(taskToTaskResponseMapper::map)
                .toList();

        return tasksResponse;
    }
}
