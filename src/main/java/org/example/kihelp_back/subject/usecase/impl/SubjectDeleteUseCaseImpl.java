package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectDeleteUseCaseImpl implements SubjectDeleteUseCase {
    private final SubjectService subjectService;

    public SubjectDeleteUseCaseImpl(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void deleteSubject(Integer id) {
        subjectService.delete(id);
    }
}
