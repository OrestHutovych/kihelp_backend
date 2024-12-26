package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentUpdateUseCaseFacade implements ArgumentUpdateUseCase {
    private final ArgumentService argumentService;

    public ArgumentUpdateUseCaseFacade(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @Override
    public void updateArgument(Long argumentId, ArgumentUpdateDto request) {
        argumentService.update(argumentId, request);
    }
}
