package org.example.kihelp_back.subject.mapper.impl;

import org.example.kihelp_back.subject.mapper.SubjectToSubjectResponseMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.model.SubjectResponse;
import org.springframework.stereotype.Component;

@Component
public class SubjectToSubjectResponseMapperImpl implements SubjectToSubjectResponseMapper {

    @Override
    public SubjectResponse map(Subject subject) {
        return new SubjectResponse(
          subject.getId(),
          subject.getName(),
          subject.getCourseNumber()
        );
    }
}
