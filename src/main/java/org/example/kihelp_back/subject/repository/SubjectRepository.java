package org.example.kihelp_back.subject.repository;

import org.example.kihelp_back.subject.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByNameAndCourseNumber(String name, int courseNumber);
    @Transactional(readOnly = true)
    List<Subject> getSubjectsByCourseNumber(int courseNumber);
}
