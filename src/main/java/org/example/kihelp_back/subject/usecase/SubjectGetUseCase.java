package org.example.kihelp_back.subject.usecase;

import org.example.kihelp_back.subject.model.SubjectResponse;

import java.util.List;

public interface SubjectGetUseCase {
    List<SubjectResponse> getSubjectsByCourseNumber(Integer courseNumber);
}
