package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArgumentGetUseCaseFacade implements ArgumentGetUseCase {
    private final ArgumentService argumentService;
    private final ArgumentMapper argumentMapper;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public ArgumentGetUseCaseFacade(ArgumentService argumentService,
                                    ArgumentMapper argumentMapper, IdEncoderApiRepository idEncoderApiRepository) {
        this.argumentService = argumentService;
        this.argumentMapper = argumentMapper;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public List<ArgumentDto> findArgumentsByTaskId(String taskId) {
        Long decodedTaskId = idEncoderApiRepository.findEncoderByName("task").decode(taskId).get(0);
        List<Argument> arguments = argumentService.getArgumentsByTaskId(decodedTaskId);

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
