package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentDeleteUseCaseFacade implements ArgumentDeleteUseCase {
    private final ArgumentService argumentService;

    public ArgumentDeleteUseCaseFacade(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @Override
    public void deleteArgument(Long argumentId) {
        argumentService.delete(argumentId);
    }
}
