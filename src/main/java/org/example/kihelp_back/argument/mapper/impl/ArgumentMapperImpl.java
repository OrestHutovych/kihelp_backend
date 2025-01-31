package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.springframework.stereotype.Component;

@Component
public class ArgumentMapperImpl implements ArgumentMapper {

    @Override
    public Argument toEntity(ArgumentCreateDto argumentCreateDto) {
        if (argumentCreateDto == null) {
            return null;
        }

        Argument argument = new Argument();

        argument.setName(argumentCreateDto.name());
        argument.setDescription(argumentCreateDto.description());

        return argument;
    }

    @Override
    public ArgumentDto toArgumentDto(Argument argument) {
        if(argument == null) {
            return null;
        }

        return new ArgumentDto(argument.getId(), argument.getName(), argument.getDescription());
    }
}
