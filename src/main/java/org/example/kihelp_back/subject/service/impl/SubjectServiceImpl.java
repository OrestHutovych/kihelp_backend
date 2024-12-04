package org.example.kihelp_back.subject.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.example.kihelp_back.subject.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NOT_FOUND;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void save(Subject subject) {
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

        subjectRepository.save(subject);
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
                .orElseThrow(() -> new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND, id)));
    }

    @Override
    public void delete(Integer id) {
        log.debug("Checking if subject with id '{}' exists", id);
        var existById = subjectRepository.existsById(id);
        log.debug("Existence check result {}", existById);

        if(!existById){
            log.warn("Subject with id '{}' not found. Throwing SubjectNotFoundException", id);
            throw new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND, id));
        }

        subjectRepository.deleteById(id);
    }

    @Override
    public void update(Integer id, String name) {
        log.debug("Attempting to find subject with id '{}'", id);
        var subject = getSubjectById(id);
        log.debug("Successfully found subject: {}", subject);

        log.debug("Updating name of subject with id '{}' to '{}'", id, name);
        subject.setName(name);
        log.debug("Successfully updated subject: {}", subject);

        subjectRepository.save(subject);
    }
}
