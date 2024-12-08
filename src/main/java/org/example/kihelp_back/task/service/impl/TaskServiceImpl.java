package org.example.kihelp_back.task.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.exception.TaskExistException;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.subject.repository.TaskRepository;
import org.example.kihelp_back.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.TASK_EXIST;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(Task task) {
        log.debug("Checking if task with title '{}' and teacher id {} exists", task.getTitle(), task.getTeacher().getId());
        var exist = taskRepository.existsByTitleAndTeacherId(task.getTitle(), task.getTeacher().getId());
        log.debug("Existence check result {}", exist);

        if (exist) {
            log.warn("Task with title {} and teacher id {} exist. Throwing TaskExistException.", task.getTitle(), task.getTeacher().getId());
            throw new TaskExistException(String.format(TASK_EXIST, task.getTitle(), task.getTeacher().getName()));
        }

        taskRepository.save(task);
    }

    @Override
    public List<Task> getByTeacher(Integer teacherId) {
        return taskRepository.findByTeacherId(teacherId);
    }
}
