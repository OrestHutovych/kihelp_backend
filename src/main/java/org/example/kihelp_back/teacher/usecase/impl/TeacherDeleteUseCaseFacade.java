package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TeacherDeleteUseCaseFacade implements TeacherDeleteUseCase {
    private final TeacherService teacherService;

    public TeacherDeleteUseCaseFacade(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherService.delete(teacherId);
    }
}
