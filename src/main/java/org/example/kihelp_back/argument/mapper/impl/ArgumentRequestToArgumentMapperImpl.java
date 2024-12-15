package org.example.kihelp_back.argument.mapper.impl;

import org.example.kihelp_back.argument.mapper.ArgumentRequestToArgumentMapper;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentRequest;
import org.springframework.stereotype.Component;

@Component
public class ArgumentRequestToArgumentMapperImpl implements ArgumentRequestToArgumentMapper {

    @Override
    public Argument map(ArgumentRequest request) {
        Argument argument = new Argument();
        argument.setName(request.name());
        argument.setDescription(request.description());
        return argument;
    }
}
