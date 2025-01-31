package org.example.kihelp_back.teacher.dto;

import org.example.kihelp_back.subject.dto.SubjectDto;

public record TeacherDto(
        String id,
        String name,
        SubjectDto subject
) {
}
