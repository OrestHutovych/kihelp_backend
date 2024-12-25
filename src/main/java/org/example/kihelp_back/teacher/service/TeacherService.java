package org.example.kihelp_back.teacher.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.teacher.exception.TeacherExistException;
import org.example.kihelp_back.teacher.exception.TeacherNotFoundException;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_ALREADY_EXIST;
import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_NOT_FOUND;

@Service
@Slf4j
public class TeacherService{
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public void create(Teacher teacher) {
        log.info("Start creating teacher for subject with ID: {}", teacher.getSubject().getId());
        boolean exist = teacherRepository.existsByNameAndSubjectId(teacher.getName(), teacher.getSubject().getId());

        if (exist) {
            throw new TeacherExistException(
                    String.format(TEACHER_ALREADY_EXIST, teacher.getName(), teacher.getSubject().getName())
            );
        }

        teacherRepository.save(teacher);
        log.info("Successfully created teacher for subject with ID: {}", teacher.getSubject().getId());
    }

    public List<Teacher> getTeachersBySubject(Long subjectId) {
        log.info("Attempting to get all teachers by subject with ID: {}", subjectId);
        return teacherRepository.findTeacherBySubjectId(subjectId);
    }

    public Teacher getTeacherById(Long id) {
        log.info("Attempting to get teacher with ID: {}", id);
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, id)));
    }

    public void delete(Long teacherId) {
        log.info("Start deleting teacher with ID: {}", teacherId);
        boolean existsById = teacherRepository.existsById(teacherId);

        if (!existsById) {
            throw new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, teacherId));
        }

        teacherRepository.deleteById(teacherId);
        log.info("Successfully deleted teacher with ID: {}", teacherId);
    }

    public void update(Long id, String name) {
        log.info("Start updating teacher with ID: {}", id);
        Teacher teacher = getTeacherById(id);
        teacher.setName(name);

        teacherRepository.save(teacher);
        log.info("Successfully updated teacher with ID: {}", id);
    }
}
