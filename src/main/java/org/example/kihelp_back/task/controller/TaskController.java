package org.example.kihelp_back.task.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.model.TaskRequest;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskCreateUseCase taskCreateUseCase;

    public TaskController(TaskCreateUseCase taskCreateUseCase) {
        this.taskCreateUseCase = taskCreateUseCase;
    }

    @PostMapping("/task")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskCreateUseCase.createTask(taskRequest);
    }
}
