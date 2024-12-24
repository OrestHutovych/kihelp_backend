package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.dto.SubjectUpdateRequest;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectUpdateUseCaseImpl implements SubjectUpdateUseCase {
    private final SubjectService subjectService;

    public SubjectUpdateUseCaseImpl(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void updateSubject(Long id, SubjectUpdateRequest subject) {
        subjectService.update(id, subject.name());
    }
}
