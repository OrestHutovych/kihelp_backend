package org.example.kihelp_back.subject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.example.kihelp_back.subject.service.SubjectService;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NOT_FOUND;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;

    public SubjectServiceImpl(SubjectRepository subjectRepository,
                              TeacherService teacherService) {
        this.subjectRepository = subjectRepository;
        this.teacherService = teacherService;
    }

    @Override
    public Subject save(Subject subject) {
        log.debug("Checking if subject with name '{}' and course number '{}' exists", subject.getName(), subject.getCourseNumber());
        var existsByNameAndCourseNumber = subjectRepository.existsByNameAndCourseNumber(subject.getName(), subject.getCourseNumber());
        log.debug("Existence check result {}", existsByNameAndCourseNumber);

        if (existsByNameAndCourseNumber) {
            log.warn("Subject with name {} and course number {} exist. Throwing SubjectExistException",
                    subject.getName(),
                    subject.getCourseNumber()
            );
            throw new SubjectExistException(String.format(SUBJECT_EXIST, subject.getName(), subject.getCourseNumber()));
        }

        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getSubjectsByCourseNumber(Integer courseNumber) {
        log.debug("Finding subject by course number {}", courseNumber);
        var subjects = subjectRepository.getSubjectsByCourseNumber(courseNumber);
        log.debug("Found {} subject for course number {}", subjects.size(), courseNumber);

        if (subjects.isEmpty()) {
            log.warn("No subject found for course number={}", subjects);
        }

        return subjects;
    }

    @Override
    public Subject getSubjectById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Subject with id {} not found. Throwing SubjectNotFoundException.", id);
                    return new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND, id));
                });
    }

    @Override
    public void delete(Integer id) {
        log.debug("Attempting to find subject with id '{}'", id);
        var subject = getSubjectById(id);
        log.debug("Successfully found subject: {}", subject);

        teacherService.delete(id);
        subjectRepository.delete(subject);
    }

    @Override
    public Subject update(Integer id, String name) {
        log.debug("Attempting to find subject with id '{}'", id);
        var subject = getSubjectById(id);
        log.debug("Successfully found subject: {}", subject);

        log.debug("Updating name of subject with id '{}' to '{}'", id, name);
        subject.setName(name);
        log.debug("Successfully updated subject: {}", subject);

        return subjectRepository.save(subject);
    }
}
