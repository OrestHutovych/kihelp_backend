package org.example.kihelp_back.task.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.model.TaskProcessRequest;
import org.example.kihelp_back.task.model.TaskProcessResponse;
import org.example.kihelp_back.task.model.TaskRequest;
import org.example.kihelp_back.task.model.TaskResponse;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.example.kihelp_back.task.usecase.TaskDeleteUseCase;
import org.example.kihelp_back.task.usecase.TaskGetUseCase;
import org.example.kihelp_back.task.usecase.TaskProcessUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskCreateUseCase taskCreateUseCase;
    private final TaskGetUseCase taskGetUseCase;
    private final TaskProcessUseCase taskProcessUseCase;
    private final TaskDeleteUseCase taskDeleteUseCase;

    public TaskController(TaskCreateUseCase taskCreateUseCase,
                          TaskGetUseCase taskGetUseCase,
                          TaskProcessUseCase taskProcessUseCase,
                          TaskDeleteUseCase taskDeleteUseCase) {
        this.taskCreateUseCase = taskCreateUseCase;
        this.taskGetUseCase = taskGetUseCase;
        this.taskProcessUseCase = taskProcessUseCase;
        this.taskDeleteUseCase = taskDeleteUseCase;
    }

    @PostMapping("/task")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskCreateUseCase.createTask(taskRequest);
    }

    @GetMapping("/task/by/teacher/{id}")
    public List<TaskResponse> getTasksByTeacher(@PathVariable("id") Integer teacherId) {
        return taskGetUseCase.getTasksByTeacher(teacherId);
    }

    @PostMapping("/task/process/{id}")
    public TaskProcessResponse processTask(@PathVariable("id") Integer taskId, @RequestBody TaskProcessRequest request) {
        return taskProcessUseCase.processTask(taskId, request);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable("id") Integer taskId) {
        taskDeleteUseCase.deleteTask(taskId);
    }
}
