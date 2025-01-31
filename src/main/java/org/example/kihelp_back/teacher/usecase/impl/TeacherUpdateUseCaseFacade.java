package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.teacher.dto.TeacherUpdateRequest;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class TeacherUpdateUseCaseFacade implements TeacherUpdateUseCase {
    private final TeacherService teacherService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TeacherUpdateUseCaseFacade(TeacherService teacherService,
                                      IdEncoderApiRepository idEncoderApiRepository) {
        this.teacherService = teacherService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void updateTeacher(String teacherId, TeacherUpdateRequest request) {
        Long teacherIdDecoded = idEncoderApiRepository.findEncoderByName("teacher").decode(teacherId).get(0);
        teacherService.update(teacherIdDecoded, request.name());
    }
}
