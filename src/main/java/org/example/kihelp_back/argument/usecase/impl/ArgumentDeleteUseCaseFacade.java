package org.example.kihelp_back.argument.usecase.impl;

import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentDeleteUseCase;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.springframework.stereotype.Component;

@Component
public class ArgumentDeleteUseCaseFacade implements ArgumentDeleteUseCase {
    private final ArgumentService argumentService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public ArgumentDeleteUseCaseFacade(ArgumentService argumentService,
                                       IdEncoderApiRepository idEncoderApiRepository) {
        this.argumentService = argumentService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void deleteArgument(String argumentId) {
        Long decodedArgumentId = idEncoderApiRepository.findEncoderByName("argument").decode(argumentId).get(0);
        argumentService.delete(decodedArgumentId);
    }
}
