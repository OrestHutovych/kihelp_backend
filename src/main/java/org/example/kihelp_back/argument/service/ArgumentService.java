package org.example.kihelp_back.argument.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.exception.ArgumentNotFoundException;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;
import org.example.kihelp_back.argument.repository.ArgumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.argument.util.MessageError.ARGUMENT_EXIST;
import static org.example.kihelp_back.argument.util.MessageError.ARGUMENT_NOT_FOUND;

@Service
@Slf4j
public class ArgumentService {
    private final ArgumentRepository argumentRepository;

    public ArgumentService(ArgumentRepository argumentRepository) {
        this.argumentRepository = argumentRepository;
    }

    @Transactional
    public void create(Argument argument) {
        log.info("Start creating argument '{}'", argument.getName());
        boolean existsExpect = argumentRepository.existsByName(argument.getName());

        if (existsExpect) {
            throw new ArgumentExistException(String.format(ARGUMENT_EXIST, argument.getName()));
        }

        Argument argumentResponse = argumentRepository.save(argument);
        log.info("Successfully created argument '{}' with ID: {}", argumentResponse.getName(), argumentResponse.getId());
    }

    public Argument getById(Long id) {
        log.info("Attempting to fetch argument with ID '{}'", id);
        return argumentRepository.findById(id)
                .orElseThrow(() -> new ArgumentNotFoundException(
                        String.format(ARGUMENT_NOT_FOUND, id)
                ));
    }

    public List<Argument> getAll() {
        log.info("Attempting to fetch all arguments");
        return argumentRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        log.info("Start to delete argument with ID '{}'", id);
        boolean existsExpect = argumentRepository.existsById(id);

        if(!existsExpect) {
            throw new ArgumentNotFoundException(String.format(ARGUMENT_NOT_FOUND, id));
        }

        argumentRepository.deleteTaskArgumentsByArgumentId(id);
        argumentRepository.deleteById(id);
        log.info("Successfully deleted argument with ID '{}'", id);
    }

    @Transactional
    public void update(Long id, ArgumentUpdateDto request) {
        log.info("Start to update argument with ID '{}'", id);
        Argument argument = getById(id);

        if(request.name() != null && !request.name().isEmpty()) {
            argument.setName(request.name());
        }

        if(request.description() != null && !request.description().isEmpty()) {
            argument.setDescription(request.description());
        }

        argumentRepository.save(argument);
        log.info("Successfully updated argument with ID '{}'", id);
    }
}
