package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.task.mapper.TaskRequestToTaskResponse;
import org.example.kihelp_back.task.model.TaskRequest;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskCreateUseCaseImpl implements TaskCreateUseCase {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final ArgumentService argumentService;
    private final TaskRequestToTaskResponse taskRequestToTaskResponse;


    public TaskCreateUseCaseImpl(TaskService taskService,
                                 TeacherService teacherService,
                                 TaskRequestToTaskResponse taskRequestToTaskResponse,
                                 ArgumentService argumentService) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.taskRequestToTaskResponse = taskRequestToTaskResponse;
        this.argumentService = argumentService;
    }

    @Override
    public void createTask(TaskRequest taskRequest) {
        var teacher = teacherService.getTeacherById(taskRequest.teacherId());
        var arguments = taskRequest.args().stream().map(argumentService::getById).collect(Collectors.toList());

        var task = taskRequestToTaskResponse.map(taskRequest, teacher, arguments);

        taskService.create(task);
    }
}
