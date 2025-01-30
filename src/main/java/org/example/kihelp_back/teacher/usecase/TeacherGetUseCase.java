package org.example.kihelp_back.teacher.usecase;

import org.example.kihelp_back.teacher.dto.TeacherDto;

import java.util.List;

public interface TeacherGetUseCase {
    TeacherDto findTeacherById(Long id);
    List<TeacherDto> getTeachersBySubject(Long subjectId);
}
