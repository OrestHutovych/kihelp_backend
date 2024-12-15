package org.example.kihelp_back.task.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.dto.TaskProcessRequest;
import org.example.kihelp_back.task.dto.TaskProcessResponse;
import org.example.kihelp_back.task.exception.TaskDeveloperNotValidException;
import org.example.kihelp_back.task.exception.TaskExistException;
import org.example.kihelp_back.task.exception.TaskNotFoundException;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskUpdateRequest;
import org.example.kihelp_back.task.model.Type;
import org.example.kihelp_back.task.repository.TaskRepository;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.*;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
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
    public TaskProcessResponse process(Integer taskId, TaskProcessRequest request) {
        var task = getById(taskId);


        return null;
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

    @Override
    public void deleteByTeacher(Integer teacherId) {
        log.debug("Attempting to find task with id '{}'", teacherId);
        var tasks = getByTeacher(teacherId);
        log.debug("Successfully found tasks: {}", tasks);

        log.debug("Deleting argument for task(s) with teacher id '{}'", teacherId);
        tasks.forEach(t -> taskRepository.deleteAllArgumentByTaskId(t.getId()));
        log.debug("Deleted argument for task(s) with teacher id '{}'", teacherId);

        taskRepository.deleteAll(tasks);
    }

    @Override
    public void update(Integer id, TaskUpdateRequest request) {
        log.debug("Updating task with id: {}", id);
        var task = getById(id);

        if (request.title() != null && !request.title().isEmpty()) {
            log.debug("Updating title to: '{}'", request.title());
            task.setTitle(request.title());
        }

        if (request.description() != null && !request.description().isEmpty()) {
            log.debug("Updating description to: '{}'", request.description());
            task.setDescription(request.description());
        }

        if (request.identifier() != null && !request.identifier().isEmpty()) {
            log.debug("Updating identifier to: '{}'", request.identifier());
            task.setIdentifier(request.identifier());
        }

        if (request.price() != null) {
            log.debug("Updating price to: {}", request.price());
            task.setPrice(request.price());
        }

        if (request.discount() != null) {
            log.debug("Updating discount to: {}", request.discount());
            task.setDiscount(request.discount());
        }

        log.debug("Updating visibility to: {}", request.visible());
        task.setVisible(request.visible());

        if (request.type() != null && !request.type().isEmpty()) {
            log.debug("Updating type to: '{}'", request.type());
            task.setType(resolveType(request.type()));
        }

        if (request.developerId() != null) {
            log.debug("Updating developer to: '{}'", request.developerId());

            log.debug("Attempting to find developer with id '{}'", request.developerId());
            var developer = userService.findById(request.developerId());

            var roles = developer.getRoles()
                    .stream()
                    .filter(r -> r.getName().equals("ROLE_DEVELOPER") || r.getName().equals("ROLE_ADMIN"))
                    .toList();

            if (roles.isEmpty()) {
                log.warn("Role is not equals Developer. Throwing TaskDeveloperNotValidException.");
                throw new TaskDeveloperNotValidException(
                        String.format(DEVELOPER_NOT_VALID, request.developerId())
                );
            }

            task.setDeveloper(developer);
        }

        log.debug("Updating auto-generate to: {}", request.autoGenerate());
        task.setAutoGenerate(request.autoGenerate());

        log.info("Saving updated task: {}", task);
        taskRepository.save(task);
    }

    private Type resolveType(String type) {
        try {
            return Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }
}
