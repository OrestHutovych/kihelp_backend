package org.example.kihelp_back.teacher.dto;

import org.example.kihelp_back.subject.dto.SubjectDto;

public record TeacherDto(
        Long id,
        String name,
        SubjectDto subject
) {
}
