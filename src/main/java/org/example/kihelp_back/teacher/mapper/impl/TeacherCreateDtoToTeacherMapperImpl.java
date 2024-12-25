package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.teacher.mapper.TeacherCreateDtoToTeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.springframework.stereotype.Component;

@Component
public class TeacherCreateDtoToTeacherMapperImpl implements TeacherCreateDtoToTeacherMapper {

    @Override
    public Teacher map(TeacherCreateDto request, Subject subject) {
        var teacher = new Teacher();
        teacher.setName(request.name());
        teacher.setSubject(subject);

        return teacher;
    }
}
