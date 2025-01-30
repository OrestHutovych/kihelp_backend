package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapperImpl implements TeacherMapper {
    private final SubjectService subjectService;

    public TeacherMapperImpl(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Teacher toEntity(TeacherCreateDto teacherCreateDto) {
        if(teacherCreateDto == null) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setName(teacherCreateDto.name());
        teacher.setSubject(subjectService.getSubjectById(teacherCreateDto.subjectId()));

        return teacher;
    }

    @Override
    public TeacherDto toTeacherDto(Teacher teacher) {
        if(teacher == null) {
            return null;
        }

        SubjectDto subject = new SubjectDto(
                teacher.getSubject().getId(),
                teacher.getSubject().getName(),
                teacher.getSubject().getCourseNumber()
        );

        return new TeacherDto(
            teacher.getId(),
            teacher.getName(),
            subject
        );
    }
}
