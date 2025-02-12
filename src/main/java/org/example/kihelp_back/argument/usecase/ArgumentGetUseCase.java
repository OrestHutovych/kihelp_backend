package org.example.kihelp_back.argument.usecase;

import org.example.kihelp_back.argument.dto.ArgumentDto;

import java.util.List;

public interface ArgumentGetUseCase {
    List<ArgumentDto> findArgumentsByTaskId(String taskId);
    List<ArgumentDto> findAllArguments();
}
