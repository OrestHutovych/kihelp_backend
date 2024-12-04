package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TeacherDeleteUseCaseImpl implements TeacherDeleteUseCase {
    private final TeacherService teacherService;

    public TeacherDeleteUseCaseImpl(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public void deleteTeacher(Integer teacherId) {
        teacherService.delete(teacherId);
    }
}
