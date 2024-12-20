package org.example.kihelp_back.teacher.usecase;

import org.example.kihelp_back.teacher.dto.TeacherResponse;

import java.util.List;

public interface TeacherGetUseCase {
    List<TeacherResponse> getTeachersBySubject(Integer subjectId);
}
