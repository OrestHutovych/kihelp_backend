package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.mapper.TeacherToTeacherResponseMapper;
import org.example.kihelp_back.teacher.dto.TeacherResponse;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TeacherGetUseCaseImpl implements TeacherGetUseCase {
    private final TeacherService teacherService;
    private final TeacherToTeacherResponseMapper teacherToTeacherResponseMapper;

    public TeacherGetUseCaseImpl(TeacherService teacherService,
                                 TeacherToTeacherResponseMapper teacherToTeacherResponseMapper) {
        this.teacherService = teacherService;
        this.teacherToTeacherResponseMapper = teacherToTeacherResponseMapper;
    }

    @Override
    public List<TeacherResponse> getTeachersBySubject(Integer subjectId) {
        log.debug("Start fetching teachers for subject ID: {}", subjectId);
        var teachers = teacherService.getTeachersBySubject(subjectId);
        log.debug("Fetched {} teacher(s) for subject ID: {}", teachers.size(), subjectId);

        log.debug("Mapping teacher(s) {} to TeacherResponse DTOs.", teachers.size());
        var teachersResponse = teachers
                .stream()
                .map(teacherToTeacherResponseMapper::map)
                .collect(Collectors.toList());
        log.debug("Successfully mapped {} teacher(s) to TeacherResponse DTOs for subject ID: {}",
                teachersResponse.size(), subjectId);

        return teachersResponse;
    }
}
