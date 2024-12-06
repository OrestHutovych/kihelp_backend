package org.example.kihelp_back.argument.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.exception.ArgumentNotFoundException;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.repository.ArgumentRepository;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.springframework.stereotype.Service;

import static org.example.kihelp_back.argument.util.MessageError.ARGUMENT_EXIST;
import static org.example.kihelp_back.argument.util.MessageError.ARGUMENT_NOT_FOUND;

@Service
@Slf4j
public class ArgumentServiceImpl implements ArgumentService {
    private final ArgumentRepository argumentRepository;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository) {
        this.argumentRepository = argumentRepository;
    }

    @Override
    public void create(Argument argument) {
        log.debug("Checking if argument with name '{}' exists", argument.getName());
        var exist = argumentRepository.existsByName(argument.getName());
        log.debug("Existence check result {}", exist);

        if (exist) {
            log.warn("Argument with name {} exist. Throwing ArgumentExistException", argument.getName());
            throw new ArgumentExistException(String.format(ARGUMENT_EXIST, argument.getName()));
        }

        argumentRepository.save(argument);
    }

    @Override
    public Argument getById(Integer id) {
        return argumentRepository.findById(id)
                .orElseThrow(() -> new ArgumentNotFoundException(
                        String.format(ARGUMENT_NOT_FOUND, id)
                ));
    }
}
