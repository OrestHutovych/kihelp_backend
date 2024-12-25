package org.example.kihelp_back.subject.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NOT_FOUND;

@Service
@Slf4j
public class SubjectService{
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Subject save(Subject subject) {
        log.info("Start saving subject with name: {}", subject.getName());
        boolean existSubject = subjectRepository.existsByNameAndCourseNumber(subject.getName(), subject.getCourseNumber());

        if (existSubject) {
            throw new SubjectExistException(
                    String.format(SUBJECT_EXIST, subject.getName(), subject.getCourseNumber())
            );
        }

        log.info("Successfully saved subject with name: {}", subject.getName());
        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByCourseNumber(Integer courseNumber) {
        log.info("Attempting to find subject(s) by course number: {}", courseNumber);
        return subjectRepository.getSubjectsByCourseNumber(courseNumber);
    }

    public Subject getSubjectById(Long id) {
        log.info("Attempting to find subject by id: {}", id);
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(
                        String.format(SUBJECT_NOT_FOUND, id))
                );
    }

    @Transactional
    public void delete(Long id) {
        log.info("Start deleting subject with id: {}", id);
        Subject subject = getSubjectById(id);

        subjectRepository.delete(subject);
        log.info("Successfully deleted subject with id: {}", id);
    }

    @Transactional
    public Subject update(Long id, String name) {
        log.info("Start updating subject with id: {}", id);
        Subject subject = getSubjectById(id);
        subject.setName(name);

        log.info("Successfully updated subject with id: {}", id);
        return subjectRepository.save(subject);
    }
}
