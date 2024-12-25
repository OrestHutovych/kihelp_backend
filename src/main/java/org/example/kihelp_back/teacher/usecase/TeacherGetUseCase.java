package org.example.kihelp_back.teacher.usecase;

import org.example.kihelp_back.teacher.dto.TeacherDto;

import java.util.List;

public interface TeacherGetUseCase {
    List<TeacherDto> getTeachersBySubject(Long subjectId);
}
