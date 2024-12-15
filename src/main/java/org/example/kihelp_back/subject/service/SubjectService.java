package org.example.kihelp_back.subject.service;

import org.example.kihelp_back.subject.model.Subject;

import java.util.List;

public interface SubjectService {
    Subject save(Subject subject);
    List<Subject> getSubjectsByCourseNumber(Integer courseNumber);
    Subject getSubjectById(Integer id);
    void delete(Integer id);
    Subject update(Integer id, String name);
}
