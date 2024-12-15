package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.task.mapper.TaskToTaskResponseMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskResponseMapperImpl implements TaskToTaskResponseMapper {

    @Override
    public TaskResponse map(Task task) {
        return new TaskResponse(
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
              task.getCreatedTimeStamp(),
              task.getArguments(),
              task.getTeacher().getId()
        );
    }
}
