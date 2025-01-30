package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.springframework.stereotype.Component;

@Component
public class ArgumentMapperImpl implements ArgumentMapper {

    @Override
    public ArgumentDto toDto(Argument argument) {
        if (argument == null) {
            return null;
        }

        return new ArgumentDto(
                argument.getName(),
                argument.getDescription()
        );
    }
}
