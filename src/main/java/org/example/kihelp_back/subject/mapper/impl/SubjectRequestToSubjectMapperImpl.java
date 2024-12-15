package org.example.kihelp_back.subject.mapper.impl;

import org.example.kihelp_back.subject.mapper.SubjectRequestToSubjectMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.dto.SubjectRequest;
import org.springframework.stereotype.Component;

@Component
public class SubjectRequestToSubjectMapperImpl implements SubjectRequestToSubjectMapper {

    @Override
    public Subject map(SubjectRequest subjectRequest) {
        var subject = new Subject();
        subject.setName(subjectRequest.name());
        subject.setCourseNumber(subjectRequest.courseNumber());
        return subject;
    }
}
