package org.example.kihelp_back.task.mapper;

import org.example.kihelp_back.task.dto.TaskCreateDto;
import org.example.kihelp_back.task.dto.TaskDto;
import org.example.kihelp_back.task.model.Task;

public interface TaskMapper {
    Task toEntity(TaskCreateDto taskCreateDto);
    TaskDto toTaskDto(Task task);
}
