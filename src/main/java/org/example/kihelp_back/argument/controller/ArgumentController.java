package org.example.kihelp_back.argument.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.argument.model.ArgumentRequest;
import org.example.kihelp_back.argument.model.ArgumentResponse;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.example.kihelp_back.argument.usecase.ArgumentDeleteUseCase;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/arguments")
public class ArgumentController {
    private final ArgumentCreateUseCase argumentCreateUseCase;
    private final ArgumentGetUseCase argumentGetUseCase;
    private final ArgumentDeleteUseCase argumentDeleteUseCase;

    public ArgumentController(ArgumentCreateUseCase argumentCreateUseCase,
                              ArgumentGetUseCase argumentGetUseCase,
                              ArgumentDeleteUseCase argumentDeleteUseCase) {
        this.argumentCreateUseCase = argumentCreateUseCase;
        this.argumentGetUseCase = argumentGetUseCase;
        this.argumentDeleteUseCase = argumentDeleteUseCase;
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
}
