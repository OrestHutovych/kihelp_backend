package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.teacher.mapper.TeacherToTeacherDtoMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.springframework.stereotype.Component;

@Component
public class TeacherToTeacherDtoMapperImpl implements TeacherToTeacherDtoMapper {

    @Override
    public TeacherDto map(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getSubject().getId()
        );
    }
}
