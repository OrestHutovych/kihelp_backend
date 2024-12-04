package org.example.kihelp_back.teacher.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.model.TeacherUpdateRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TeacherUpdateUseCase {
    void updateTeacher(Integer teacherId, @Valid TeacherUpdateRequest request);
}
