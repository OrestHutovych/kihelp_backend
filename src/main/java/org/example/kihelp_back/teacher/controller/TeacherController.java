package org.example.kihelp_back.teacher.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.model.TeacherRequest;
import org.example.kihelp_back.teacher.model.TeacherResponse;
import org.example.kihelp_back.teacher.model.TeacherUpdateRequest;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherDeleteUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    private final TeacherCreateUseCase teacherCreateUseCase;
    private final TeacherGetUseCase teacherGetUseCase;
    private final TeacherDeleteUseCase teacherDeleteUseCase;
    private final TeacherUpdateUseCase teacherUpdateUseCase;

    public TeacherController(TeacherCreateUseCase teacherCreateUseCase,
                             TeacherGetUseCase teacherGetUseCase,
                             TeacherDeleteUseCase teacherDeleteUseCase,
                             TeacherUpdateUseCase teacherUpdateUseCase) {
        this.teacherCreateUseCase = teacherCreateUseCase;
        this.teacherGetUseCase = teacherGetUseCase;
        this.teacherDeleteUseCase = teacherDeleteUseCase;
        this.teacherUpdateUseCase = teacherUpdateUseCase;
    }

    @PostMapping("/teacher")
    public void createTeacher(@Valid @RequestBody TeacherRequest request){
        teacherCreateUseCase.createTeacher(request);
    }

    @GetMapping("/teacher/by/subject/{id}")
    public List<TeacherResponse> getTeachersBySubject(@PathVariable("id") Integer subjectId){
        return teacherGetUseCase.getTeachersBySubject(subjectId);
    }

    @DeleteMapping("/teacher/{id}")
    public void deleteTeacher(@PathVariable("id") Integer teacherId){
        teacherDeleteUseCase.deleteTeacher(teacherId);
    }

    @PutMapping("/teacher/{id}")
    public void updateTeacher(@PathVariable("id") Integer teacherId, @Valid @RequestBody TeacherUpdateRequest request){
        teacherUpdateUseCase.updateTeacher(teacherId, request);
    }
}
