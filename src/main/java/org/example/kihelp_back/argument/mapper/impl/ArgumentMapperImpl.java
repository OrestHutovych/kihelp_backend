package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.mapper.ArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArgumentMapperImpl implements ArgumentMapper {

    private final IdEncoderApiRepository idEncoderApiRepository;

    public ArgumentMapperImpl(IdEncoderApiRepository idEncoderApiRepository) {
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

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

        return new ArgumentDto(
                encodeArgumentID(argument.getId()),
                argument.getName(),
                argument.getDescription()
        );
    }

    public String encodeArgumentID(Long argumentId) {
        return idEncoderApiRepository.findEncoderByName("argument").encode(List.of(argumentId));
    }
}
