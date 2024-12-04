package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.teacher.mapper.TeacherToTeacherResponseMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.model.TeacherResponse;
import org.springframework.stereotype.Component;

@Component
public class TeacherToTeacherResponseMapperImpl implements TeacherToTeacherResponseMapper {

    @Override
    public TeacherResponse map(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getSubject().getId()
        );
    }
}
