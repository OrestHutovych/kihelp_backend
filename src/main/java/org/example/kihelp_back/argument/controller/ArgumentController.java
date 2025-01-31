package org.example.kihelp_back.argument.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.argument.dto.ArgumentCreateDto;
import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.dto.ArgumentUpdateDto;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.example.kihelp_back.argument.usecase.ArgumentDeleteUseCase;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.example.kihelp_back.argument.usecase.ArgumentUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/arguments")
public class ArgumentController {
    private final ArgumentCreateUseCase argumentCreateUseCase;
    private final ArgumentGetUseCase argumentGetUseCase;
    private final ArgumentDeleteUseCase argumentDeleteUseCase;
    private final ArgumentUpdateUseCase argumentUpdateUseCase;

    public ArgumentController(ArgumentCreateUseCase argumentCreateUseCase,
                              ArgumentGetUseCase argumentGetUseCase,
                              ArgumentDeleteUseCase argumentDeleteUseCase,
                              ArgumentUpdateUseCase argumentUpdateUseCase) {
        this.argumentCreateUseCase = argumentCreateUseCase;
        this.argumentGetUseCase = argumentGetUseCase;
        this.argumentDeleteUseCase = argumentDeleteUseCase;
        this.argumentUpdateUseCase = argumentUpdateUseCase;
    }

    @PostMapping("/argument")
    public void create(@Valid @RequestBody ArgumentCreateDto request) {
        argumentCreateUseCase.createArgument(request);
    }

    @GetMapping("/")
    public List<ArgumentDto> getArguments() {
        return argumentGetUseCase.findAllArguments();
    }

    @DeleteMapping("/argument/{id}")
    public void deleteArgument(@PathVariable("id") Long argumentId) {
        argumentDeleteUseCase.deleteArgument(argumentId);
    }

    @PatchMapping("/argument/{id}")
    public void updateArgument(@PathVariable("id") Long argumentId,
                               @RequestBody ArgumentUpdateDto request) {
        argumentUpdateUseCase.updateArgument(argumentId, request);
    }
}
