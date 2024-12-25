package org.example.kihelp_back.subject.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.mapper.SubjectCreateDtoToSubjectMapper;
import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SubjectCreateUseCaseFacade implements SubjectCreateUseCase {
    private final SubjectService subjectService;
    private final SubjectCreateDtoToSubjectMapper subjectCreateDtoToSubjectMapper;

    public SubjectCreateUseCaseFacade(SubjectService subjectService,
                                      SubjectCreateDtoToSubjectMapper subjectCreateDtoToSubjectMapper) {
        this.subjectService = subjectService;
        this.subjectCreateDtoToSubjectMapper = subjectCreateDtoToSubjectMapper;
    }

    @Override
    public void create(SubjectCreateDto request) {
        log.info("Attempting to mapping SubjectRequest to Subject: {}", request.name());
        Subject subject = subjectCreateDtoToSubjectMapper.map(request);

        subjectService.save(subject);
    }
}
