package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.mapper.TeacherRequestToTeacherMapper;
import org.example.kihelp_back.teacher.model.TeacherRequest;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TeacherCreateUseCaseImpl implements TeacherCreateUseCase {
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final TeacherRequestToTeacherMapper teacherRequestToTeacherMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherCreateUseCaseImpl.class);


    public TeacherCreateUseCaseImpl(TeacherService teacherService,
                                    SubjectService subjectService,
                                    TeacherRequestToTeacherMapper teacherRequestToTeacherMapper) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.teacherRequestToTeacherMapper = teacherRequestToTeacherMapper;
    }

    @Override
    public void createTeacher(TeacherRequest request) {
        LOGGER.debug("Fetching subject with ID: {}", request.subjectId());
        var subject = subjectService.getSubjectById(request.subjectId());
        LOGGER.debug("Fetched subject: {}", subject);

        LOGGER.debug("Mapping request to teacher entity...");
        var teacher = teacherRequestToTeacherMapper.map(request, subject);
        LOGGER.debug("Mapped teacher entity: {}", teacher);

        teacherService.create(teacher);
        LOGGER.info("Teacher created successfully: {}", teacher);
    }
}
