package org.example.kihelp_back.task.service.impl;

import org.example.kihelp_back.task.exception.TaskExistException;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.repository.TaskRepository;
import org.example.kihelp_back.task.service.TaskService;
import org.springframework.stereotype.Service;

import static org.example.kihelp_back.task.util.ErrorMessage.TASK_EXIST;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(Task task) {
        var exist = taskRepository.existsByTitleAndTeacherId(task.getTitle(), task.getTeacher().getId());

        if (exist) {
            throw new TaskExistException(String.format(TASK_EXIST, task.getTitle(), task.getTeacher().getName()));
        }

        taskRepository.save(task);
    }
}
