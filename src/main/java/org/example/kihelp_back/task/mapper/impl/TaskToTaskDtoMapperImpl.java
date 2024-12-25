package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.task.mapper.TaskToTaskDtoMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoMapperImpl implements TaskToTaskDtoMapper {

    @Override
    public TaskDto map(Task task) {
        return new TaskDto(
              task.getId(),
              task.getTitle(),
              task.getDescription(),
              task.getIdentifier(),
              task.getPrice(),
              task.getDiscount(),
              task.isVisible(),
              task.getType().name(),
              task.getDeveloper().getId(),
              task.isAutoGenerate(),
              task.getCreatedAt(),
              task.getArguments(),
              task.getTeacher().getId()
        );
    }
}
