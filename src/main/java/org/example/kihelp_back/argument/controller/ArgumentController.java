package org.example.kihelp_back.argument.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.argument.model.ArgumentRequest;
import org.example.kihelp_back.argument.usecase.ArgumentCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/arguments")
public class ArgumentController {
    private final ArgumentCreateUseCase argumentCreateUseCase;

    public ArgumentController(ArgumentCreateUseCase argumentCreateUseCase) {
        this.argumentCreateUseCase = argumentCreateUseCase;
    }

    @PostMapping("/argument")
    public void create(@Valid @RequestBody ArgumentRequest request) {
        argumentCreateUseCase.createArgument(request);
    }
}
