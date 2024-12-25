package org.example.kihelp_back.subject.mapper.impl;

import org.example.kihelp_back.subject.mapper.SubjectToSubjectResponseMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.springframework.stereotype.Component;

@Component
public class SubjectToSubjectResponseMapperImpl implements SubjectToSubjectResponseMapper {

    @Override
    public SubjectDto map(Subject subject) {
        return new SubjectDto(
          subject.getId(),
          subject.getName(),
          subject.getCourseNumber()
        );
    }
}
