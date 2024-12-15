package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.teacher.mapper.TeacherRequestToTeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.dto.TeacherRequest;
import org.springframework.stereotype.Component;

@Component
public class TeacherRequestToTeacherMapperImpl implements TeacherRequestToTeacherMapper {

    @Override
    public Teacher map(TeacherRequest request, Subject subject) {
        var teacher = new Teacher();
        teacher.setName(request.name());
        teacher.setSubject(subject);

        return teacher;
    }
}
