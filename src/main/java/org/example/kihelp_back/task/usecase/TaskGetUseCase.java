package org.example.kihelp_back.task.usecase;

import org.example.kihelp_back.task.dto.TaskResponse;

import java.util.List;

public interface TaskGetUseCase {
    List<TaskResponse> getTasksByTeacher(Integer teacherId);
}
