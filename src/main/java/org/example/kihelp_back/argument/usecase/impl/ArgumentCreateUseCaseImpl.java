package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.mapper.ArgumentRequestToArgumentMapper;
import org.example.kihelp_back.argument.model.ArgumentRequest;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.springframework.stereotype.Component;

@Component
public class ArgumentCreateUseCaseImpl implements ArgumentCreateUseCase {
    private final ArgumentService argumentService;
    private final ArgumentRequestToArgumentMapper argumentRequestToArgumentMapper;

    public ArgumentCreateUseCaseImpl(ArgumentService argumentService,
                                     ArgumentRequestToArgumentMapper argumentRequestToArgumentMapper) {
        this.argumentService = argumentService;
        this.argumentRequestToArgumentMapper = argumentRequestToArgumentMapper;
    }

    @Override
    public void createArgument(ArgumentRequest request) {
        var argument = argumentRequestToArgumentMapper.map(request);

        argumentService.create(argument);
    }
}
