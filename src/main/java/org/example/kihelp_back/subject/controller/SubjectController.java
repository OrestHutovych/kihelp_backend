package org.example.kihelp_back.subject.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.subject.dto.SubjectCreateDto;
import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.subject.dto.SubjectUpdateDto;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.example.kihelp_back.subject.usecase.SubjectDeleteUseCase;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.example.kihelp_back.subject.usecase.SubjectUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private final SubjectCreateUseCase subjectCreateUseCase;
    private final SubjectGetUseCase subjectGetUseCase;
    private final SubjectDeleteUseCase subjectDeleteUseCase;
    private final SubjectUpdateUseCase subjectUpdateUseCase;

    public SubjectController(SubjectCreateUseCase subjectCreateUseCase,
                             SubjectGetUseCase subjectGetUseCase,
                             SubjectDeleteUseCase subjectDeleteUseCase,
                             SubjectUpdateUseCase subjectUpdateUseCase) {
        this.subjectCreateUseCase = subjectCreateUseCase;
        this.subjectGetUseCase = subjectGetUseCase;
        this.subjectDeleteUseCase = subjectDeleteUseCase;
        this.subjectUpdateUseCase = subjectUpdateUseCase;
    }

    @PostMapping("/subject")
    public void createSubject(@Valid @RequestBody SubjectCreateDto request) {
        subjectCreateUseCase.create(request);
    }

    @GetMapping("/course-number/{number}")
    public List<SubjectDto> getSubjectByCourseNumber(@PathVariable("number") Integer number) {
        return subjectGetUseCase.getSubjectsByCourseNumber(number);
    }

    @PutMapping("/{subject_id}")
    public void updateSubject(@PathVariable("subject_id") Long subjectId,
                              @Valid @RequestBody SubjectUpdateDto request) {
        subjectUpdateUseCase.updateSubject(subjectId, request);
    }

    @DeleteMapping("/{subject_id}")
    public void deleteSubject(@PathVariable("subject_id") Long subjectId) {
        subjectDeleteUseCase.deleteSubject(subjectId);
    }
}
