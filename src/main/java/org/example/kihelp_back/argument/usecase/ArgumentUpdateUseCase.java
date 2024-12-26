package org.example.kihelp_back.argument.usecase;

import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;

public interface ArgumentUpdateUseCase {
    void updateArgument(Long argumentId, ArgumentUpdateDto request);
}
