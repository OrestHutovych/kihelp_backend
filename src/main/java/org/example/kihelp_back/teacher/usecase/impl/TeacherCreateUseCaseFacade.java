package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TeacherCreateUseCaseFacade implements TeacherCreateUseCase {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    public TeacherCreateUseCaseFacade(TeacherService teacherService,
                                      TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public void createTeacher(TeacherCreateDto request) {
        Teacher teacher = teacherMapper.toEntity(request);

        teacherService.create(teacher);
    }
}
