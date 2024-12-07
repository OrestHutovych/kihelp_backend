package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentDeleteUseCaseImpl implements ArgumentDeleteUseCase {
    private final ArgumentService argumentService;

    public ArgumentDeleteUseCaseImpl(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @Override
    public void deleteArgument(Integer argumentId) {
        argumentService.delete(argumentId);
    }
}
