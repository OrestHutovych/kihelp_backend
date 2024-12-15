package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.mapper.ArgumentToArgumentResponseMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentResponse;
import org.springframework.stereotype.Component;

@Component
public class ArgumentToArgumentResponseMapperImpl implements ArgumentToArgumentResponseMapper {

    @Override
    public ArgumentResponse map(Argument argument) {
        return new ArgumentResponse(
                argument.getId(),
                argument.getName(),
                argument.getDescription()
        );
    }
}
