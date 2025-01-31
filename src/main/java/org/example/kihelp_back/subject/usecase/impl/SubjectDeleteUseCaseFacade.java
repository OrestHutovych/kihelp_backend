package org.example.kihelp_back.subject.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.subject.usecase.SubjectDeleteUseCase;
import org.springframework.stereotype.Component;

@Component
public class SubjectDeleteUseCaseFacade implements SubjectDeleteUseCase {
    private final SubjectService subjectService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public SubjectDeleteUseCaseFacade(SubjectService subjectService,
                                      IdEncoderApiRepository idEncoderApiRepository) {
        this.subjectService = subjectService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public void deleteSubject(String id) {
        Long subjectIdDecoded = idEncoderApiRepository.findEncoderByName("subject").decode(id).get(0);
        subjectService.delete(subjectIdDecoded);
    }
}
