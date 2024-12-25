package org.example.kihelp_back.subject.usecase;

import org.example.kihelp_back.subject.dto.SubjectDto;

import java.util.List;

public interface SubjectGetUseCase {
    List<SubjectDto> getSubjectsByCourseNumber(Integer courseNumber);
}
