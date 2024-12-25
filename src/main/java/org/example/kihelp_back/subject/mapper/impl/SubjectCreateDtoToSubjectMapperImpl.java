package org.example.kihelp_back.subject.mapper.impl;

import org.example.kihelp_back.subject.mapper.SubjectCreateDtoToSubjectMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.springframework.stereotype.Component;

@Component
public class SubjectCreateDtoToSubjectMapperImpl implements SubjectCreateDtoToSubjectMapper {

    @Override
    public Subject map(SubjectCreateDto subjectRequest) {
        Subject subject = new Subject();
        subject.setName(subjectRequest.name());
        subject.setCourseNumber(subjectRequest.courseNumber());
        return subject;
    }
}
