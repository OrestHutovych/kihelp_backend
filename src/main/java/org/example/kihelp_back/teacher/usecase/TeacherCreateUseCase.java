package org.example.kihelp_back.teacher.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.model.TeacherRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TeacherCreateUseCase {
    void createTeacher(@Valid TeacherRequest request);
}
