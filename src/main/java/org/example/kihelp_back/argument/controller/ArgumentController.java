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
@RequestMapping("/api/v1/argument")
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

    @PostMapping("/create")
    public void create(@Valid @RequestBody ArgumentCreateDto request) {
        argumentCreateUseCase.createArgument(request);
    }

    @GetMapping("/all")
    public List<ArgumentDto> getArguments() {
        return argumentGetUseCase.getArguments();
    }

    @DeleteMapping("/delete/{argument_id}")
    public void deleteArgument(@PathVariable("argument_id") Long argumentId) {
        argumentDeleteUseCase.deleteArgument(argumentId);
    }

    @PatchMapping("/update/{argument_id}")
    public void updateArgument(@PathVariable("argument_id") Long argumentId,
                               @Valid @RequestBody ArgumentUpdateDto request) {
        argumentUpdateUseCase.updateArgument(argumentId, request);
    }
}
