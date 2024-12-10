package org.example.kihelp_back.teacher.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.teacher.exception.TeacherExistException;
import org.example.kihelp_back.teacher.exception.TeacherNotFoundException;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.repository.TeacherRepository;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_ALREADY_EXIST;
import static org.example.kihelp_back.teacher.util.ErrorMessage.TEACHER_NOT_FOUND;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TaskService taskService;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              TaskService taskService) {
        this.teacherRepository = teacherRepository;
        this.taskService = taskService;
    }

    @Override
    public void create(Teacher teacher) {
        log.debug("Checking if teacher exists by name {} and subjectId {}", teacher.getName(), teacher.getSubject().getId());
        var exist = teacherRepository.existsByNameAndSubjectId(teacher.getName(), teacher.getSubject().getId());
        log.debug("Existence check result for teacher {}: {}", teacher.getName(), exist);

        if (exist) {
            log.warn("Teacher with name {} and subjectId {} exist. Throwing TeacherExistException", teacher.getName(), teacher.getSubject().getId());
            throw new TeacherExistException(String.format(TEACHER_ALREADY_EXIST, teacher.getName(), teacher.getSubject().getName()));
        }

        teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getTeachersBySubject(Integer subjectId) {
        log.debug("Finding teachers by subject id {}", subjectId);
        var teachers = teacherRepository.findTeacherBySubjectId(subjectId);
        log.debug("Found {} teachers for subject id {}", teachers.size(), subjectId);

        if (teachers.isEmpty()) {
            log.warn("No teachers found for subjectId={}", subjectId);
        }

        return teachers;
    }

    @Override
    public Teacher getTeacherById(Integer id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, id)));
    }

    @Override
    public void delete(Integer teacherId) {
        log.debug("Checking if teacher with id '{}' exists", teacherId);
        var existsById = teacherRepository.existsById(teacherId);
        log.debug("Existence check result for teacherId {}: {}", teacherId, existsById);

        if (!existsById) {
            log.warn("Teacher with id '{}' not found. Throwing TeacherNotFoundException", teacherId);
            throw new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, teacherId));
        }

        taskService.deleteByTeacher(teacherId);
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public void update(Integer id, String name) {
        log.debug("Fetching teacher with id '{}'", id);
        var teacher = getTeacherById(id);
        log.debug("Successfully found teacher: {}", teacher);

        log.debug("Updating name of teacher with id '{}' from '{}' to '{}'", id, teacher.getName(), name);
        teacher.setName(name);
        log.debug("Successfully updated teacher: {}", teacher);

        teacherRepository.save(teacher);
    }
}
