package org.example.kihelp_back.teacher.repository;

import org.example.kihelp_back.teacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByNameAndSubjectId(String name, int subjectId);
    List<Teacher> findTeacherBySubjectId(int subjectId);
}
