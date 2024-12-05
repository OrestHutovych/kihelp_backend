package org.example.kihelp_back.task.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.task.model.TaskRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TaskCreateUseCase {
    void createTask(@Valid TaskRequest taskRequest);
}
