package org.example.kihelp_back.argument.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.mapper.ArgumentToArgumentResponseMapper;
import org.example.kihelp_back.argument.model.ArgumentResponse;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ArgumentGetUseCaseImpl implements ArgumentGetUseCase {
    private final ArgumentService argumentService;
    private final ArgumentToArgumentResponseMapper argumentToArgumentResponseMapper;

    public ArgumentGetUseCaseImpl(ArgumentService argumentService,
                                  ArgumentToArgumentResponseMapper argumentToArgumentResponseMapper) {
        this.argumentService = argumentService;
        this.argumentToArgumentResponseMapper = argumentToArgumentResponseMapper;
    }

    @Override
    public List<ArgumentResponse> getArguments() {
        log.debug("Start fetching arguments");
        var arguments = argumentService.getAll();
        log.debug("Fetched {} argument(s).", arguments.size());

        log.debug("Mapping argument(s) {} to ArgumentResponse DTOs.", arguments.size());
        var argumentResponse = arguments.stream()
                .map(argumentToArgumentResponseMapper::map)
                .toList();
        log.debug("Successfully mapped {} argument(s) to ArgumentResponse DTOs.", argumentResponse.size());

        return argumentResponse;
    }
}
