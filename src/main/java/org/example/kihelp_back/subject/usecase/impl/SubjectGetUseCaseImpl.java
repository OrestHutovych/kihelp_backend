package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.mapper.SubjectToSubjectResponseMapper;
import org.example.kihelp_back.subject.model.SubjectResponse;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectGetUseCaseImpl implements SubjectGetUseCase {
    private final SubjectService subjectService;
    private final SubjectToSubjectResponseMapper subjectToSubjectResponseMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectGetUseCaseImpl.class);

    public SubjectGetUseCaseImpl(SubjectService subjectService,
                                 SubjectToSubjectResponseMapper subjectToSubjectResponseMapper) {
        this.subjectService = subjectService;
        this.subjectToSubjectResponseMapper = subjectToSubjectResponseMapper;
    }

    @Override
    public List<SubjectResponse> getSubjectsByCourseNumber(Integer courseNumber) {
        LOGGER.debug("Starting get subjects by course number {}", courseNumber);
        var subjects = subjectService.getSubjectsByCourseNumber(courseNumber);
        LOGGER.debug("Ending get subjects by course number {}", courseNumber);
        LOGGER.debug("Starting transformation subject {} to subject response", subjects);
        var subjectsResponse = subjects.stream().map(subjectToSubjectResponseMapper::map).toList();
        LOGGER.debug("Transformation complete for subjectsResponse {}", subjectsResponse);

        LOGGER.info("Successfully get subjects {} by course number {}",subjectsResponse, courseNumber);
        return subjectsResponse;
    }
}
