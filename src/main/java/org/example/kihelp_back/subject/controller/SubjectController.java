package org.example.kihelp_back.subject.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.subject.dto.SubjectRequest;
import org.example.kihelp_back.subject.dto.SubjectResponse;
import org.example.kihelp_back.subject.dto.SubjectUpdateRequest;
import org.example.kihelp_back.subject.usecase.SubjectCreateUseCase;
import org.example.kihelp_back.subject.usecase.SubjectDeleteUseCase;
import org.example.kihelp_back.subject.usecase.SubjectGetUseCase;
import org.example.kihelp_back.subject.usecase.SubjectUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
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
    public void createSubject(@Valid @RequestBody SubjectRequest request) {
        subjectCreateUseCase.create(request);
    }

    @GetMapping("/subject/by/course_number/{number}")
    public List<SubjectResponse> getSubjectByCourseNumber(@PathVariable("number") Integer number) {
        return subjectGetUseCase.getSubjectsByCourseNumber(number);
    }

    @PutMapping("/subject/{id}")
    public void updateSubject(@PathVariable("id") Integer id, @Valid @RequestBody SubjectUpdateRequest request) {
        subjectUpdateUseCase.updateSubject(id, request);
    }

    @DeleteMapping("/subject/{id}")
    public void deleteSubject(@PathVariable("id") Integer id) {
        subjectDeleteUseCase.deleteSubject(id);
    }
}
