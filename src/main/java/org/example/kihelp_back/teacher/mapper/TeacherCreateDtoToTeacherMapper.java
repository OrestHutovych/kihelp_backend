package org.example.kihelp_back.teacher.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;

public interface TeacherCreateDtoToTeacherMapper extends MapperV2<Teacher, TeacherCreateDto, Subject> {
}
