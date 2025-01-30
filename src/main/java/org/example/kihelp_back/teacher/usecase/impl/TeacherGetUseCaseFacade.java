package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherGetUseCaseFacade implements TeacherGetUseCase {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    public TeacherGetUseCaseFacade(TeacherService teacherService,
                                   TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDto findTeacherById(Long id) {
        Teacher teacher = teacherService.findTeacherById(id);

        return teacherMapper.toTeacherDto(teacher);
    }

    @Override
    public List<TeacherDto> getTeachersBySubject(Long subjectId) {
        List<Teacher> teachers = teacherService.getTeachersBySubject(subjectId);

        return teachers
                .stream()
                .map(teacherMapper::toTeacherDto)
                .toList();
    }
}
