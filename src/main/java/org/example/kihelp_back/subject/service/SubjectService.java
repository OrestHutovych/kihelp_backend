package org.example.kihelp_back.subject.service;

import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.subject.util.SubjectErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.SubjectErrorMessages.SUBJECT_NOT_FOUND;

@Service
public class SubjectService{
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Subject save(Subject subject) {
        boolean existSubject = subjectRepository.existsByNameAndCourseNumber(subject.getName(), subject.getCourseNumber());

        if (existSubject) {
            throw new SubjectExistException(
                    String.format(SUBJECT_EXIST, subject.getName(), subject.getCourseNumber())
            );
        }

        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjectsByCourseNumber(Integer courseNumber) {
        return subjectRepository.getSubjectsByCourseNumber(courseNumber);
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(
                        String.format(SUBJECT_NOT_FOUND, id))
                );
    }

    @Transactional
    public void delete(Long id) {
        Subject subject = getSubjectById(id);

        subjectRepository.delete(subject);
    }

    @Transactional
    public Subject update(Long id, String name) {
        Subject subject = getSubjectById(id);
        subject.setName(name);

        return subjectRepository.save(subject);
    }
}
