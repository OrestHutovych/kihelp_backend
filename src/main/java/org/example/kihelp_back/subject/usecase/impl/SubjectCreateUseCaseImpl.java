package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.mapper.SubjectRequestToSubjectMapper;
import org.example.kihelp_back.subject.model.SubjectRequest;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubjectCreateUseCaseImpl implements SubjectCreateUseCase {
    private final SubjectService subjectService;
    private final SubjectRequestToSubjectMapper subjectRequestToSubjectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectCreateUseCaseImpl.class);

    public SubjectCreateUseCaseImpl(SubjectService subjectService, SubjectRequestToSubjectMapper subjectRequestToSubjectMapper) {
        this.subjectService = subjectService;
        this.subjectRequestToSubjectMapper = subjectRequestToSubjectMapper;
    }

    @Override
    public void create(SubjectRequest request) {
        LOGGER.debug("Starting transformation of SubjectRequest: {}", request);
        var subject = subjectRequestToSubjectMapper.map(request);
        LOGGER.debug("Transformation complete for SubjectRequest: {}", request);

        subjectService.save(subject);
        LOGGER.info("Subject creation completed for: {}", subject);
    }
}
