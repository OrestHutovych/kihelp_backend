package org.example.kihelp_back.argument.mapper;

import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.model.Argument;

public interface ArgumentMapper {
    Argument toEntity(ArgumentCreateDto argumentCreateDto);
    ArgumentDto toArgumentDto(Argument argument);
}
