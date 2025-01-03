package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.teacher.dto.TeacherUpdateRequest;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateUseCaseFacade implements TeacherUpdateUseCase {
    private final TeacherService teacherService;

    public TeacherUpdateUseCaseFacade(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void updateTeacher(Long teacherId, TeacherUpdateRequest request) {
        teacherService.update(teacherId, request.name());
    }
}
