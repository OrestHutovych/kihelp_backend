package org.example.kihelp_back.teacher.usecase;

import org.example.kihelp_back.teacher.dto.TeacherDto;

import java.util.List;

public interface TeacherGetUseCase {
    TeacherDto findTeacherById(String id);
    List<TeacherDto> getTeachersBySubject(String subjectId);
}
