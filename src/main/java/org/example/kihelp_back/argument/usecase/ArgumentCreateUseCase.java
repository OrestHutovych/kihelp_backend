package org.example.kihelp_back.argument.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.argument.model.ArgumentRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ArgumentCreateUseCase {
    void createArgument(@Valid ArgumentRequest request);
}
