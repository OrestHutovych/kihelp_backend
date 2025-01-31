package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArgumentGetUseCaseFacade implements ArgumentGetUseCase {
    private final ArgumentService argumentService;
    private final ArgumentMapper argumentMapper;

    public ArgumentGetUseCaseFacade(ArgumentService argumentService,
                                    ArgumentMapper argumentMapper) {
        this.argumentService = argumentService;
        this.argumentMapper = argumentMapper;
    }

    @Override
    public List<ArgumentDto> findArgumentsByTaskId(Long taskId) {
        List<Argument> arguments = argumentService.getArgumentsByTaskId(taskId);

        return arguments.stream()
                .map(argumentMapper::toArgumentDto)
                .toList();
    }

    @Override
    public List<ArgumentDto> findAllArguments() {
        List<Argument> arguments = argumentService.getAll();

        return arguments.stream()
                .map(argumentMapper::toArgumentDto)
                .toList();
    }
}
