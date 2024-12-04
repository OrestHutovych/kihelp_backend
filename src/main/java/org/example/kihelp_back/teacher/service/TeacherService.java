package org.example.kihelp_back.teacher.service;

import org.example.kihelp_back.teacher.model.Teacher;

import java.util.List;

public interface TeacherService {
    void create(Teacher teacher);
    List<Teacher> getTeachersBySubject(Integer subjectId);
    Teacher getTeacherById(Integer id);
    void delete(Integer teacherId);
    void update(Integer id, String name);
}
