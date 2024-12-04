package org.example.kihelp_back.teacher.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.model.TeacherRequest;
import org.example.kihelp_back.teacher.model.TeacherResponse;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    private final TeacherCreateUseCase teacherCreateUseCase;
    private final TeacherGetUseCase teacherGetUseCase;

    public TeacherController(TeacherCreateUseCase teacherCreateUseCase,
                             TeacherGetUseCase teacherGetUseCase) {
        this.teacherCreateUseCase = teacherCreateUseCase;
        this.teacherGetUseCase = teacherGetUseCase;
    }

    @PostMapping("/teacher")
    public void createTeacher(@Valid @RequestBody TeacherRequest request){
        teacherCreateUseCase.createTeacher(request);
    }

    @GetMapping("/teacher/by/subject/{id}")
    public List<TeacherResponse> getTeachersBySubject(@PathVariable("id") Integer subjectId){
        return teacherGetUseCase.getTeachersBySubject(subjectId);
    }
}
