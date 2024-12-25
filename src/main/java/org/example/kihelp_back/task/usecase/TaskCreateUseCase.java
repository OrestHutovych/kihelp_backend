package org.example.kihelp_back.task.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.dto.TaskCreateDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TaskCreateUseCase {
    void createTask(@Valid TaskCreateDto taskRequest);
}
