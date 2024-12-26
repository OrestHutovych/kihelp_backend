package org.example.kihelp_back.argument.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.mapper.ArgumentCreateDtoToArgumentMapper;
import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ArgumentCreateUseCaseFacade implements ArgumentCreateUseCase {
    private final ArgumentService argumentService;
    private final ArgumentCreateDtoToArgumentMapper argumentCreateDtoToArgumentMapper;

    public ArgumentCreateUseCaseFacade(ArgumentService argumentService,
                                       ArgumentCreateDtoToArgumentMapper argumentCreateDtoToArgumentMapper) {
        this.argumentService = argumentService;
        this.argumentCreateDtoToArgumentMapper = argumentCreateDtoToArgumentMapper;
    }

    @Override
    public void createArgument(ArgumentCreateDto request) {
        log.info("Attempting to map ArgumentCreateDto '{}' to Argument", request.name());
        Argument argument = argumentCreateDtoToArgumentMapper.map(request);

        argumentService.create(argument);
    }
}
