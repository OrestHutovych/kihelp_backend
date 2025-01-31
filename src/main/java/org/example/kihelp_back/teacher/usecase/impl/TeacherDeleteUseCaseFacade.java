package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class TeacherDeleteUseCaseFacade implements TeacherDeleteUseCase {
    private final TeacherService teacherService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TeacherDeleteUseCaseFacade(TeacherService teacherService,
                                      IdEncoderApiRepository idEncoderApiRepository) {
        this.teacherService = teacherService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void deleteTeacher(String teacherId) {
        Long teacherIdDecoded = idEncoderApiRepository.findEncoderByName("teacher").decode(teacherId).get(0);
        teacherService.delete(teacherIdDecoded);
    }
}
