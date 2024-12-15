package org.example.kihelp_back.argument.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.argument.dto.ArgumentRequest;
import org.example.kihelp_back.argument.dto.ArgumentResponse;
import org.example.kihelp_back.argument.dto.ArgumentUpdateRequest;
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
    public void create(@Valid @RequestBody ArgumentRequest request) {
        argumentCreateUseCase.createArgument(request);
    }

    @GetMapping("/argument")
    public List<ArgumentResponse> getArguments() {
        return argumentGetUseCase.getArguments();
    }

    @DeleteMapping("/argument/{id}")
    public void deleteArgument(@PathVariable("id") Integer argumentId) {
        argumentDeleteUseCase.deleteArgument(argumentId);
    }

    @PatchMapping("/argument/{id}")
    public void updateArgument(@PathVariable("id") Integer argumentId, @Valid @RequestBody ArgumentUpdateRequest request) {
        argumentUpdateUseCase.updateArgument(argumentId, request);
    }
}
