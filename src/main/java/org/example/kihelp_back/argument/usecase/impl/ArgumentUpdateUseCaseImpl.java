package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.model.ArgumentUpdateRequest;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentUpdateUseCaseImpl implements ArgumentUpdateUseCase {
    private final ArgumentService argumentService;

    public ArgumentUpdateUseCaseImpl(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @Override
    public void updateArgument(Integer argumentId, ArgumentUpdateRequest request) {
        argumentService.update(argumentId, request);
    }
}
