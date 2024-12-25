package org.example.kihelp_back.subject.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.mapper.SubjectToSubjectResponseMapper;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SubjectGetUseCaseFacade implements SubjectGetUseCase {
    private final SubjectService subjectService;
    private final SubjectToSubjectResponseMapper subjectToSubjectResponseMapper;

    public SubjectGetUseCaseFacade(SubjectService subjectService,
                                   SubjectToSubjectResponseMapper subjectToSubjectResponseMapper) {
        this.subjectService = subjectService;
        this.subjectToSubjectResponseMapper = subjectToSubjectResponseMapper;
    }

    @Override
    public List<SubjectDto> getSubjectsByCourseNumber(Integer courseNumber) {
        List<Subject> subjects = subjectService.getSubjectsByCourseNumber(courseNumber);

        log.debug("Mapping subjects(s) {} to SubjectResponse DTOs.", subjects.size());
        return subjects
                .stream()
                .map(subjectToSubjectResponseMapper::map)
                .toList();
    }
}
