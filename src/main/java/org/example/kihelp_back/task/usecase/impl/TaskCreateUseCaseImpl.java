package org.example.kihelp_back.task.usecase.impl;

import org.example.kihelp_back.task.mapper.TaskRequestToTaskResponse;
import org.example.kihelp_back.task.model.TaskRequest;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateUseCaseImpl implements TaskCreateUseCase {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final TaskRequestToTaskResponse taskRequestToTaskResponse;

    public TaskCreateUseCaseImpl(TaskService taskService,
                                 TeacherService teacherService,
                                 TaskRequestToTaskResponse taskRequestToTaskResponse) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.taskRequestToTaskResponse = taskRequestToTaskResponse;
    }

    @Override
    public void createTask(TaskRequest taskRequest) {
        var teacher = teacherService.getTeacherById(taskRequest.teacherId());
        var task = taskRequestToTaskResponse.map(taskRequest, teacher);

        taskService.create(task);
    }
}
