package org.example.kihelp_back.support.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.support.dto.SupportDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SupportCreateUseCase {
    void sendMessage(@Valid SupportDto supportDto);
}
