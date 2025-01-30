package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.example.kihelp_back.subject.mapper.SubjectMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectCreateUseCaseFacade implements SubjectCreateUseCase {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    public SubjectCreateUseCaseFacade(SubjectService subjectService,
                                      SubjectMapper subjectMapper1) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper1;
    }

    @Override
    public void create(SubjectCreateDto request) {
        Subject subject = subjectMapper.toEntity(request);

        subjectService.save(subject);
    }
}
