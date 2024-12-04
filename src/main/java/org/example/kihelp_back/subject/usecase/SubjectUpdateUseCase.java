package org.example.kihelp_back.subject.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.subject.model.SubjectUpdateRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SubjectUpdateUseCase {
    void updateSubject(Integer id, @Valid SubjectUpdateRequest subject);
}
