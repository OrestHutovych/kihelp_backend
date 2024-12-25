package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.mapper.TeacherCreateDtoToTeacherMapper;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TeacherCreateUseCaseFacade implements TeacherCreateUseCase {
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final TeacherCreateDtoToTeacherMapper teacherCreateDtoToTeacherMapper;

    public TeacherCreateUseCaseFacade(TeacherService teacherService,
                                      SubjectService subjectService,
                                      TeacherCreateDtoToTeacherMapper teacherCreateDtoToTeacherMapper) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.teacherCreateDtoToTeacherMapper = teacherCreateDtoToTeacherMapper;
    }

    @Override
    public void createTeacher(TeacherCreateDto request) {
        Subject subject = subjectService.getSubjectById(request.subjectId());

        log.info("Attempting to map TeacherCreateDto {} to Teacher", request.name());
        Teacher teacher = teacherCreateDtoToTeacherMapper.map(request, subject);

        teacherService.create(teacher);
    }
}
