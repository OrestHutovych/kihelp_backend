package org.example.kihelp_back.argument.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.mapper.ArgumentToArgumentDtoMapper;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ArgumentGetUseCaseFacade implements ArgumentGetUseCase {
    private final ArgumentService argumentService;
    private final ArgumentToArgumentDtoMapper argumentToArgumentDtoMapper;

    public ArgumentGetUseCaseFacade(ArgumentService argumentService,
                                    ArgumentToArgumentDtoMapper argumentToArgumentDtoMapper) {
        this.argumentService = argumentService;
        this.argumentToArgumentDtoMapper = argumentToArgumentDtoMapper;
    }

    @Override
    public List<ArgumentDto> getArguments() {
        List<Argument> arguments = argumentService.getAll();

        log.info("Attempting to map Argument(s) {} to ArgumentDto and return it.", arguments.size());
        return arguments.stream()
                .map(argumentToArgumentDtoMapper::map)
                .toList();
    }
}
