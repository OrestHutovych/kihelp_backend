package org.example.kihelp_back.task.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.dto.*;
import org.example.kihelp_back.task.usecase.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
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

    @PostMapping("/create")
    public void createTask(@Valid @RequestBody TaskCreateDto taskRequest) {
        taskCreateUseCase.createTask(taskRequest);
    }

    @PostMapping("/process/{task_id}")
    public TaskProcessDto processTask(@PathVariable("task_id") Long taskId,
                                      @RequestBody TaskProcessCreateDto request) {
        return taskProcessUseCase.processTask(taskId, request);
    }

    @GetMapping("/getTasksByTeacher/{teacher_id}")
    public List<TaskDto> getTasksByTeacher(@PathVariable("teacher_id") Long teacherId) {
        return taskGetUseCase.getTasksByTeacher(teacherId);
    }

    @PatchMapping("/update/{task_id}")
    public void updateTask(@PathVariable("task_id") Long taskId,
                           @RequestBody TaskUpdateDto request){
        taskUpdateUseCase.updateTask(taskId, request);
    }

    @DeleteMapping("/delete/{task_id}")
    public void deleteTask(@PathVariable("task_id") Long taskId) {
        taskDeleteUseCase.deleteTask(taskId);
    }
}
