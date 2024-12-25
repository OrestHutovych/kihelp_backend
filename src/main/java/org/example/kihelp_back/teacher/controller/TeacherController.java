package org.example.kihelp_back.teacher.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.teacher.dto.TeacherCreateDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.dto.TeacherUpdateRequest;
import org.example.kihelp_back.teacher.usecase.TeacherCreateUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherDeleteUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherGetUseCase;
import org.example.kihelp_back.teacher.usecase.TeacherUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
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

    @PostMapping("/create")
    public void createTeacher(@Valid @RequestBody TeacherCreateDto request){
        teacherCreateUseCase.createTeacher(request);
    }

    @GetMapping("/getBySubject/{subject_id}")
    public List<TeacherDto> getTeachersBySubject(@PathVariable("subject_id") Long subjectId){
        return teacherGetUseCase.getTeachersBySubject(subjectId);
    }

    @DeleteMapping("/delete/{teacher_id}")
    public void deleteTeacher(@PathVariable("teacher_id") Long teacherId){
        teacherDeleteUseCase.deleteTeacher(teacherId);
    }

    @PutMapping("/changeAnInitials/{teacher_id}")
    public void updateTeacher(@PathVariable("teacher_id") Long teacherId,
                              @Valid @RequestBody TeacherUpdateRequest request){
        teacherUpdateUseCase.updateTeacher(teacherId, request);
    }
}
