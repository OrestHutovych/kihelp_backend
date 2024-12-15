package org.example.kihelp_back.subject.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.mapper.SubjectRequestToSubjectMapper;
import org.example.kihelp_back.subject.dto.SubjectRequest;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SubjectCreateUseCaseImpl implements SubjectCreateUseCase {
    private final SubjectService subjectService;
    private final SubjectRequestToSubjectMapper subjectRequestToSubjectMapper;

    public SubjectCreateUseCaseImpl(SubjectService subjectService, SubjectRequestToSubjectMapper subjectRequestToSubjectMapper) {
        this.subjectService = subjectService;
        this.subjectRequestToSubjectMapper = subjectRequestToSubjectMapper;
    }

    @Override
    public void create(SubjectRequest request) {
        log.debug("Mapping SubjectRequest {} to Subject entity", request);
        var subject = subjectRequestToSubjectMapper.map(request);
        log.debug("Mapped Subject entity {}", subject);

        subjectService.save(subject);
    }
}
