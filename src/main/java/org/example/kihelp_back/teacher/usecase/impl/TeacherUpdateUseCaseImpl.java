package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.teacher.model.TeacherUpdateRequest;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateUseCaseImpl implements TeacherUpdateUseCase {
    private final TeacherService teacherService;

    public TeacherUpdateUseCaseImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void updateTeacher(Integer teacherId, TeacherUpdateRequest request) {
        teacherService.update(teacherId, request.name());
    }
}
