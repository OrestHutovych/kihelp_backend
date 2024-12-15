package org.example.kihelp_back.subject.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.subject.dto.SubjectRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SubjectCreateUseCase {
    void create(@Valid SubjectRequest request);
}
