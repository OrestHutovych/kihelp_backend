package org.example.kihelp_back.subject.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.subject.dto.SubjectUpdateDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SubjectUpdateUseCase {
    void updateSubject(String id, @Valid SubjectUpdateDto subject);
}
