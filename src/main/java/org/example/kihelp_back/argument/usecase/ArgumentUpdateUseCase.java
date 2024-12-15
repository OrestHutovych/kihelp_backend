package org.example.kihelp_back.argument.usecase;

import org.example.kihelp_back.argument.dto.ArgumentUpdateRequest;

public interface ArgumentUpdateUseCase {
    void updateArgument(Integer argumentId, ArgumentUpdateRequest request);
}
