package org.example.kihelp_back.teacher.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TeacherCreateUseCase {
    void createTeacher(@Valid TeacherCreateDto request);
}
