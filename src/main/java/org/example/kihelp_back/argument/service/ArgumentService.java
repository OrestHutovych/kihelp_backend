package org.example.kihelp_back.argument.service;

import org.example.kihelp_back.argument.exception.ArgumentExistException;
import org.example.kihelp_back.argument.exception.ArgumentNotFoundException;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;
import org.example.kihelp_back.argument.repository.ArgumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.argument.util.ArgumentMessageError.ARGUMENT_EXIST;
import static org.example.kihelp_back.argument.util.ArgumentMessageError.ARGUMENT_NOT_FOUND;

@Service
public class ArgumentService {
    private final ArgumentRepository argumentRepository;

    public ArgumentService(ArgumentRepository argumentRepository) {
        this.argumentRepository = argumentRepository;
    }

    @Transactional
    public void create(Argument argument) {
        boolean existsExpect = argumentRepository.existsByName(argument.getName());

        if (existsExpect) {
            throw new ArgumentExistException(String.format(ARGUMENT_EXIST, argument.getName()));
        }

        argumentRepository.save(argument);
    }

    public Argument getById(Long id) {
        return argumentRepository.findById(id)
                .orElseThrow(() -> new ArgumentNotFoundException(
                        String.format(ARGUMENT_NOT_FOUND, id)
                ));
    }

    public List<Argument> getArgumentsByTaskId(Long taskId) {
        return argumentRepository.getArgumentsByTaskId(taskId);
    }

    public List<Argument> getAll() {
        return argumentRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Argument argument = getById(id);

        argumentRepository.deleteTaskArgumentsByArgumentId(id);
        argumentRepository.delete(argument);
    }

    @Transactional
    public void update(Long id, ArgumentUpdateDto request) {
        Argument argument = getById(id);

        if(request.name() != null && !request.name().isEmpty()) {
            argument.setName(request.name());
        }

        if(request.description() != null && !request.description().isEmpty()) {
            argument.setDescription(request.description());
        }

        argumentRepository.save(argument);
    }
}
