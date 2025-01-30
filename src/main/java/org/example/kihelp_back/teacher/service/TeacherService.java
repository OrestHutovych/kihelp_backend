package org.example.kihelp_back.teacher.service;

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
public class TeacherService{
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public void create(Teacher teacher) {
        boolean exist = teacherRepository.existsByNameAndSubjectId(teacher.getName(), teacher.getSubject().getId());

        if (exist) {
            throw new TeacherExistException(
                    String.format(TEACHER_ALREADY_EXIST, teacher.getName(), teacher.getSubject().getName())
            );
        }

        teacherRepository.save(teacher);
    }

    public List<Teacher> getTeachersBySubject(Long subjectId) {
        return teacherRepository.findTeacherBySubjectId(subjectId);
    }

    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, id)));
    }

    public void delete(Long teacherId) {
        boolean existsById = teacherRepository.existsById(teacherId);

        if (!existsById) {
            throw new TeacherNotFoundException(String.format(TEACHER_NOT_FOUND, teacherId));
        }

        teacherRepository.deleteById(teacherId);
    }

    public void update(Long id, String name) {
        Teacher teacher = findTeacherById(id);

        teacher.setName(name);

        teacherRepository.save(teacher);
    }
}
