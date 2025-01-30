package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.subject.mapper.SubjectMapper;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectGetUseCaseFacade implements SubjectGetUseCase {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    public SubjectGetUseCaseFacade(SubjectService subjectService,
                                   SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<SubjectDto> getSubjectsByCourseNumber(Integer courseNumber) {
        List<Subject> subjects = subjectService.getSubjectsByCourseNumber(courseNumber);

        return subjects
                .stream()
                .map(subjectMapper::toSubjectDto)
                .toList();
    }
}
