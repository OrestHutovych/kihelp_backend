package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentUpdateUseCase;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.springframework.stereotype.Component;

@Component
public class ArgumentUpdateUseCaseFacade implements ArgumentUpdateUseCase {
    private final ArgumentService argumentService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public ArgumentUpdateUseCaseFacade(ArgumentService argumentService,
                                       IdEncoderApiRepository idEncoderApiRepository) {
        this.argumentService = argumentService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void updateArgument(String argumentId, ArgumentUpdateDto request) {
        Long decodedArgumentId = idEncoderApiRepository.findEncoderByName("argument").decode(argumentId).get(0);
        argumentService.update(decodedArgumentId, request);
    }
}
