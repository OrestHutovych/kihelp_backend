package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectDeleteUseCaseFacade implements SubjectDeleteUseCase {
    private final SubjectService subjectService;

    public SubjectDeleteUseCaseFacade(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void deleteSubject(Long id) {
        subjectService.delete(id);
    }
}
