package org.example.kihelp_back.teacher.mapper;

import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.model.Teacher;

public interface TeacherMapper {
    Teacher toEntity(TeacherCreateDto teacherCreateDto);
    TeacherDto toTeacherDto(Teacher teacher);
}
