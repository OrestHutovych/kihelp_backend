package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.dto.SubjectUpdateDto;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectUpdateUseCaseFacade implements SubjectUpdateUseCase {
    private final SubjectService subjectService;

    public SubjectUpdateUseCaseFacade(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void updateSubject(Long id, SubjectUpdateDto subject) {
        subjectService.update(id, subject.name());
    }
}
