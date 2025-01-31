package org.example.kihelp_back.teacher.mapper.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.mapper.SubjectMapper;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeacherMapperImpl implements TeacherMapper {
    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TeacherMapperImpl(SubjectService subjectService,
                             SubjectMapper subjectMapper, IdEncoderApiRepository idEncoderApiRepository) {
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public Teacher toEntity(TeacherCreateDto teacherCreateDto) {
        if(teacherCreateDto == null) {
            return null;
        }

        Teacher teacher = new Teacher();

        teacher.setName(teacherCreateDto.name());
        teacher.setSubject(subjectService.getSubjectById(decodedSubjectId(teacherCreateDto.subjectId())));

        return teacher;
    }

    @Override
    public TeacherDto toTeacherDto(Teacher teacher) {
        if(teacher == null) {
            return null;
        }

        SubjectDto subject = subjectMapper.toSubjectDto(teacher.getSubject());

        return new TeacherDto(
                encodedTeacherId(teacher.getId()),
                teacher.getName(),
                subject
        );
    }

    public String encodedTeacherId(Long id) {
        return idEncoderApiRepository.findEncoderByName("teacher").encode(List.of(id));
    }

    public Long decodedSubjectId(String encodedSubjectId) {
        return idEncoderApiRepository.findEncoderByName("subject").decode(encodedSubjectId).get(0);
    }
}
