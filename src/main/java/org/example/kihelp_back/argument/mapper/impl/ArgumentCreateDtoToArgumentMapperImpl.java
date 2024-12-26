package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.mapper.ArgumentCreateDtoToArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.springframework.stereotype.Component;

@Component
public class ArgumentCreateDtoToArgumentMapperImpl implements ArgumentCreateDtoToArgumentMapper {

    @Override
    public Argument map(ArgumentCreateDto request) {
        Argument argument = new Argument();
        argument.setName(request.name());
        argument.setDescription(request.description());
        return argument;
    }
}
