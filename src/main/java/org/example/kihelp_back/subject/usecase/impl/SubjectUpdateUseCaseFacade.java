package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.subject.dto.SubjectUpdateDto;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectUpdateUseCaseFacade implements SubjectUpdateUseCase {
    private final SubjectService subjectService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public SubjectUpdateUseCaseFacade(SubjectService subjectService,
                                      IdEncoderApiRepository idEncoderApiRepository) {
        this.subjectService = subjectService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void updateSubject(String id, SubjectUpdateDto subject) {
        Long subjectIdDecoded = idEncoderApiRepository.findEncoderByName("subject").decode(id).get(0);
        subjectService.update(subjectIdDecoded, subject.name());
    }
}
