package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.task.mapper.TaskToTaskResponseMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.model.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskResponseMapperImpl implements TaskToTaskResponseMapper {

    @Override
    public TaskResponse map(Task task) {
        return new TaskResponse(
              task.getId(),
              task.getTitle(),
              task.getDescription(),
              task.getPath(),
              task.getPrice(),
              task.getDiscount(),
              task.isVisible(),
              task.getType().name(),
              task.getDeveloper(),
              task.getCreatedTimeStamp(),
              task.getArguments(),
              task.getTeacher().getId()
        );
    }
}
