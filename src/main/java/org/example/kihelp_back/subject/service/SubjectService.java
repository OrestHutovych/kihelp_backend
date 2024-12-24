package org.example.kihelp_back.subject.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NOT_FOUND;

@Service
@Slf4j
public class SubjectService{
    private final SubjectRepository subjectRepository;
    private final TeacherService teacherService;

    public SubjectService(SubjectRepository subjectRepository,
                          TeacherService teacherService) {
        this.subjectRepository = subjectRepository;
        this.teacherService = teacherService;
    }

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

    public void delete(Long id) {
        Subject subject = getSubjectById(id);

//        teacherService.delete(id);
        subjectRepository.delete(subject);
    }

    public Subject update(Long id, String name) {
        log.debug("Attempting to find subject with id '{}'", id);
        var subject = getSubjectById(id);
        log.debug("Successfully found subject: {}", subject);

        log.debug("Updating name of subject with id '{}' to '{}'", id, name);
        subject.setName(name);
        log.debug("Successfully updated subject: {}", subject);

        return subjectRepository.save(subject);
    }
}
