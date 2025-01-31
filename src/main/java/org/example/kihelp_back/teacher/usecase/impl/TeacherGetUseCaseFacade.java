package org.example.kihelp_back.teacher.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherGetUseCaseFacade implements TeacherGetUseCase {
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TeacherGetUseCaseFacade(TeacherService teacherService,
                                   TeacherMapper teacherMapper, IdEncoderApiRepository idEncoderApiRepository) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public TeacherDto findTeacherById(String id) {
        Long teacherIdDecoded = idEncoderApiRepository.findEncoderByName("teacher").decode(id).get(0);
        Teacher teacher = teacherService.findTeacherById(teacherIdDecoded);

        return teacherMapper.toTeacherDto(teacher);
    }

    @Override
    public List<TeacherDto> getTeachersBySubject(String subjectId) {
        Long subjectIdDecoded = idEncoderApiRepository.findEncoderByName("subject").decode(subjectId).get(0);
        List<Teacher> teachers = teacherService.getTeachersBySubject(subjectIdDecoded);

        return teachers
                .stream()
                .map(teacherMapper::toTeacherDto)
                .toList();
    }
}
