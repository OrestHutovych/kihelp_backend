package org.example.kihelp_back.subject.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.mapper.SubjectToSubjectResponseMapper;
import org.example.kihelp_back.subject.model.SubjectResponse;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SubjectGetUseCaseImpl implements SubjectGetUseCase {
    private final SubjectService subjectService;
    private final SubjectToSubjectResponseMapper subjectToSubjectResponseMapper;

    public SubjectGetUseCaseImpl(SubjectService subjectService,
                                 SubjectToSubjectResponseMapper subjectToSubjectResponseMapper) {
        this.subjectService = subjectService;
        this.subjectToSubjectResponseMapper = subjectToSubjectResponseMapper;
    }

    @Override
    public List<SubjectResponse> getSubjectsByCourseNumber(Integer courseNumber) {
        log.debug("Start fetching subject for course number: {}", courseNumber);
        var subjects = subjectService.getSubjectsByCourseNumber(courseNumber);
        log.debug("Fetched {} subjects(s) for course number: {}", subjects.size(), courseNumber);

        log.debug("Mapping subjects(s) {} to SubjectResponse DTOs.", subjects.size());
        var subjectsResponse = subjects
                .stream()
                .map(subjectToSubjectResponseMapper::map)
                .toList();
        log.debug("Successfully mapped {} subjects(s) to SubjectResponse DTOs for course number: {}",
                subjectsResponse.size(), courseNumber
        );

        return subjectsResponse;
    }
}
