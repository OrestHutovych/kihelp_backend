package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentCreateUseCaseFacade implements ArgumentCreateUseCase {
    private final ArgumentService argumentService;
    private final ArgumentMapper argumentMapper;

    public ArgumentCreateUseCaseFacade(ArgumentService argumentService,
                                       ArgumentMapper argumentMapper) {
        this.argumentService = argumentService;
        this.argumentMapper = argumentMapper;
    }

    @Override
    public void createArgument(ArgumentCreateDto request) {
        Argument argument = argumentMapper.toEntity(request);

        argumentService.create(argument);
    }
}
