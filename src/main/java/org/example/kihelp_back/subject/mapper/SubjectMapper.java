package org.example.kihelp_back.subject.mapper;

import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.model.Subject;

public interface SubjectMapper {
    Subject toEntity(SubjectCreateDto subjectCreateDto);
    SubjectDto toSubjectDto(Subject subject);
}
