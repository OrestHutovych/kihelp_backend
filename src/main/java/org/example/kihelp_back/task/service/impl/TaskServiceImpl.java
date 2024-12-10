package org.example.kihelp_back.task.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.exception.TaskExistException;
import org.example.kihelp_back.task.exception.TaskNotFoundException;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.subject.repository.TaskRepository;
import org.example.kihelp_back.task.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.TASK_EXIST;
import static org.example.kihelp_back.task.util.ErrorMessage.TASK_NOT_FOUND;

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

    @Override
    public Task getById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task with id {} not found. Throwing TaskNotFoundException.", id);
                    return new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
                });
    }

    @Override
    public void delete(Integer id) {
       log.debug("Attempting to find task with id '{}'", id);
       var task = getById(id);
       log.debug("Successfully found task: {}", task);

       log.debug("Deleting argument for task with id '{}'", id);
       taskRepository.deleteAllArgumentByTaskId(id);
       log.debug("Deleted argument for task with id '{}'", id);

       taskRepository.delete(task);
    }
}
