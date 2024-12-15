package org.example.kihelp_back.argument.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.mapper.ArgumentRequestToArgumentMapper;
import org.example.kihelp_back.argument.dto.ArgumentRequest;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        log.debug("Mapping ArgumentRequest {} to Argument entity", request);
        var argument = argumentRequestToArgumentMapper.map(request);
        log.debug("Mapped Argument entity {}", argument);

        argumentService.create(argument);
    }
}
