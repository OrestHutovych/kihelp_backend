package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskDto;

import java.util.List;

public interface TaskGetUseCase {
    List<TaskDto> getTasksByTeacher(String teacherId);
}
