package org.example.kihelp_back.argument.service.impl;

import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.repository.ArgumentRepository;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.springframework.stereotype.Service;

import static org.example.kihelp_back.argument.util.MessageError.ARGUMENT_EXIST;

@Service
public class ArgumentServiceImpl implements ArgumentService {
    private final ArgumentRepository argumentRepository;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository) {
        this.argumentRepository = argumentRepository;
    }

    @Override
    public void create(Argument argument) {
        var exist = argumentRepository.existsByName(argument.getName());

        if (exist) {
            throw new ArgumentExistException(String.format(ARGUMENT_EXIST, argument.getName()));
        }

        argumentRepository.save(argument);
    }
}
