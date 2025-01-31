package org.example.kihelp_back.subject.mapper.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.mapper.SubjectMapper;
import org.example.kihelp_back.subject.model.Subject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectMapperImpl implements SubjectMapper {

    private final IdEncoderApiRepository idEncoderApiRepository;

    public SubjectMapperImpl(IdEncoderApiRepository idEncoderApiRepository) {
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public Subject toEntity(SubjectCreateDto subjectCreateDto) {
        if(subjectCreateDto == null) {
            return null;
        }
        Subject subject = new Subject();

        subject.setName(subjectCreateDto.name());
        subject.setCourseNumber(subjectCreateDto.courseNumber());

        return subject;
    }

    @Override
    public SubjectDto toSubjectDto(Subject subject) {
        if(subject == null) {
            return null;
        }

        return new SubjectDto(
                encodedSubjectId(subject.getId()),
                subject.getName(),
                subject.getCourseNumber()
        );
    }

    public String encodedSubjectId(Long id) {
        return idEncoderApiRepository.findEncoderByName("subject").encode(List.of(id));
    }
}
