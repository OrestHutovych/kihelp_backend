package org.example.kihelp_back.support.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.support.dto.SupportDto;
import org.example.kihelp_back.support.usecase.SupportCreateUseCase;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/supports")
public class SupportController {
    private final SupportCreateUseCase supportCreateUseCase;

    public SupportController(SupportCreateUseCase supportCreateUseCase) {
        this.supportCreateUseCase = supportCreateUseCase;
    }

    @PostMapping("/sent")
    public void sendMessage(@ModelAttribute @Valid SupportDto supportDto) {
        supportCreateUseCase.sendMessage(supportDto);
    }
}
