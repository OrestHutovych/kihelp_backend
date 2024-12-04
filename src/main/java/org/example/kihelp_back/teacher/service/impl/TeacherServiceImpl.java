package org.example.kihelp_back.teacher.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.exception.TeacherExistException;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.repository.TeacherRepository;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_ALREADY_EXIST;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug("Starting teacher creation: name={}, subject={}", teacher.getName(), teacher.getSubject().getName());

        var exist = teacherRepository.existsByNameAndSubjectId(teacher.getName(), teacher.getSubject().getId());
        log.debug("Checking existence of teacher with name={} and subject ID={}: exists={}",
                teacher.getName(), teacher.getSubject().getId(), exist);

        if (exist) {
            String errorMessage = String.format(TEACHER_ALREADY_EXIST, teacher.getName(), teacher.getSubject().getName());
            throw new TeacherExistException(errorMessage);
        }

        teacherRepository.save(teacher);
        log.info("Teacher successfully created: {}", teacher);
    }

    @Override
    public List<Teacher> getTeachersBySubject(Integer subjectId) {
        var teachers = teacherRepository.findTeacherBySubjectId(subjectId);

        if (teachers.isEmpty()) {
            log.warn("No teachers found for subjectId={}", subjectId);
        }

        log.info("Returning teacher list for subjectId={}", subjectId);
        return teachers;
    }

}
