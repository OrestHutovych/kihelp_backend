package org.example.kihelp_back.subject.service.impl;

import org.example.kihelp_back.subject.exception.SubjectExistException;
import org.example.kihelp_back.subject.exception.SubjectNotFoundException;
import org.example.kihelp_back.subject.model.Subject;
import org.example.kihelp_back.subject.repository.SubjectRepository;
import org.example.kihelp_back.subject.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_EXIST;
import static org.example.kihelp_back.subject.util.ErrorMessages.SUBJECT_NOT_FOUND;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void save(Subject subject) {
        LOGGER.debug("Checking if subject with name '{}' and course number '{}' exists", subject.getName(), subject.getCourseNumber());
        var existsByNameAndCourseNumber = subjectRepository.existsByNameAndCourseNumber(subject.getName(), subject.getCourseNumber());
        LOGGER.debug("Existence check result {}", existsByNameAndCourseNumber);

        if (existsByNameAndCourseNumber) {
            throw new SubjectExistException(String.format(SUBJECT_EXIST, subject.getName(), subject.getCourseNumber()));
        }

        subjectRepository.save(subject);
        LOGGER.info("Subject saved successfully: {}", subject);
    }

    @Override
    public List<Subject> getSubjectsByCourseNumber(Integer courseNumber) {
        return subjectRepository.getSubjectsByCourseNumber(courseNumber);
    }

    @Override
    public Subject getSubjectById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND, id)));
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Checking if subject with id '{}' exists", id);
        var existById = subjectRepository.existsById(id);
        LOGGER.debug("Existence check result {}", existById);

        if(!existById){
            throw new SubjectNotFoundException(String.format(SUBJECT_NOT_FOUND, id));
        }

        subjectRepository.deleteById(id);
        LOGGER.info("Subject deleted successfully: {}", id);
    }

    @Override
    public void update(Integer id, String name) {
        LOGGER.debug("Attempting to find subject with id '{}'", id);
        var subject = getSubjectById(id);
        LOGGER.debug("Successfully found subject: {}", subject);

        LOGGER.debug("Updating name of subject with id '{}' to '{}'", id, name);
        subject.setName(name);

        LOGGER.debug("Saving updated subject: {}", subject);
        subjectRepository.save(subject);

        LOGGER.info("Subject with id '{}' updated successfully: {}", id, subject);
    }
}
