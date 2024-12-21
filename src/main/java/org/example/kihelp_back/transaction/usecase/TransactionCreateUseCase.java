package org.example.kihelp_back.transaction.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.transaction.dto.TransactionCreateDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TransactionCreateUseCase {
    void createTransaction(@Valid TransactionCreateDto request);
}
