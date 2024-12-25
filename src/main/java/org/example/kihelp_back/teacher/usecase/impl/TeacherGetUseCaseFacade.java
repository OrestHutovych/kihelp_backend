package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.mapper.TeacherToTeacherDtoMapper;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TeacherGetUseCaseFacade implements TeacherGetUseCase {
    private final TeacherService teacherService;
    private final TeacherToTeacherDtoMapper teacherToTeacherDtoMapper;

    public TeacherGetUseCaseFacade(TeacherService teacherService,
                                   TeacherToTeacherDtoMapper teacherToTeacherDtoMapper) {
        this.teacherService = teacherService;
        this.teacherToTeacherDtoMapper = teacherToTeacherDtoMapper;
    }

    @Override
    public List<TeacherDto> getTeachersBySubject(Long subjectId) {
        List<Teacher> teachers = teacherService.getTeachersBySubject(subjectId);

        log.info("Attempting to map Teacher(s) {} to TeacherDto.", teachers.size());
        return teachers
                .stream()
                .map(teacherToTeacherDtoMapper::map)
                .collect(Collectors.toList());
    }
}
