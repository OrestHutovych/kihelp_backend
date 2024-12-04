package org.example.kihelp_back.teacher.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.mapper.TeacherRequestToTeacherMapper;
import org.example.kihelp_back.teacher.model.TeacherRequest;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TeacherCreateUseCaseImpl implements TeacherCreateUseCase {
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final TeacherRequestToTeacherMapper teacherRequestToTeacherMapper;

    public TeacherCreateUseCaseImpl(TeacherService teacherService,
                                    SubjectService subjectService,
                                    TeacherRequestToTeacherMapper teacherRequestToTeacherMapper) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.teacherRequestToTeacherMapper = teacherRequestToTeacherMapper;
    }

    @Override
    public void createTeacher(TeacherRequest request) {
        log.debug("Fetching subject with ID: {}", request.subjectId());
        var subject = subjectService.getSubjectById(request.subjectId());
        log.debug("Successfully fetched subject: {}", subject);

        log.debug("Mapping TeacherRequest {} to Teacher entity.", request);
        var teacher = teacherRequestToTeacherMapper.map(request, subject);
        log.debug("Mapped Teacher entity: {}", teacher);


        teacherService.create(teacher);
    }
}
