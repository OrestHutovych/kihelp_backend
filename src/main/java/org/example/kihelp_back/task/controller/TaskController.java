package org.example.kihelp_back.task.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.dto.*;
import org.example.kihelp_back.task.usecase.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskCreateUseCase taskCreateUseCase;
    private final TaskGetUseCase taskGetUseCase;
    private final TaskProcessUseCase taskProcessUseCase;
    private final TaskDeleteUseCase taskDeleteUseCase;
    private final TaskUpdateUseCase taskUpdateUseCase;

    public TaskController(TaskCreateUseCase taskCreateUseCase,
                          TaskGetUseCase taskGetUseCase,
                          TaskProcessUseCase taskProcessUseCase,
                          TaskDeleteUseCase taskDeleteUseCase,
                          TaskUpdateUseCase taskUpdateUseCase) {
        this.taskCreateUseCase = taskCreateUseCase;
        this.taskGetUseCase = taskGetUseCase;
        this.taskProcessUseCase = taskProcessUseCase;
        this.taskDeleteUseCase = taskDeleteUseCase;
        this.taskUpdateUseCase = taskUpdateUseCase;
    }

    @PostMapping("/task")
    public void createTask(@Valid @RequestBody TaskCreateDto taskRequest) {
        taskCreateUseCase.createTask(taskRequest);
    }

    @PostMapping("/process")
    public Map<String, String> processTask(@RequestBody TaskProcessCreateDto request) {
        return taskProcessUseCase.processTask(request);
    }

    @GetMapping("/teacher/{teacher_id}")
    public List<TaskDto> getTasksByTeacher(@PathVariable("teacher_id") String teacherId) {
        return taskGetUseCase.getTasksByTeacher(teacherId);
    }

    @PatchMapping("/{task_id}")
    public void updateTask(@PathVariable("task_id") String taskId,
                           @RequestBody TaskUpdateDto request){
        taskUpdateUseCase.updateTask(taskId, request);
    }

    @DeleteMapping("/{task_id}")
    public void deleteTask(@PathVariable("task_id") String taskId) {
        taskDeleteUseCase.deleteTask(taskId);
    }
}
