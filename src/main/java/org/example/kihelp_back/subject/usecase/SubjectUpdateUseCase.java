package org.example.kihelp_back.subject.usecase;

import org.example.kihelp_back.subject.model.SubjectUpdateRequest;

public interface SubjectUpdateUseCase {
    void updateSubject(Integer id, SubjectUpdateRequest subject);
}
