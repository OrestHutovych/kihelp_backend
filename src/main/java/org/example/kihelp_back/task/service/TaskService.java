package org.example.kihelp_back.task.service;

import org.example.kihelp_back.task.model.Task;

import java.util.List;

public interface TaskService {
    void create(Task task);
    List<Task> getByTeacher(Integer teacherId);
    Task getById(Integer id);
    void delete(Integer id);
    void deleteByTeacher(Integer teacherId);
}
