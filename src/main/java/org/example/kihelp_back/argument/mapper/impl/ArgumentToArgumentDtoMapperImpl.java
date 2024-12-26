package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.mapper.ArgumentToArgumentDtoMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.springframework.stereotype.Component;

@Component
public class ArgumentToArgumentDtoMapperImpl implements ArgumentToArgumentDtoMapper {

    @Override
    public ArgumentDto map(Argument argument) {
        return new ArgumentDto(
                argument.getId(),
                argument.getName(),
                argument.getDescription()
        );
    }
}
