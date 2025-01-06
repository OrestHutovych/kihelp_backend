package org.example.kihelp_back.task.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.task.dto.*;
import org.example.kihelp_back.task.exception.*;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.model.TaskType;
import org.example.kihelp_back.task.repository.TaskRepository;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.*;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final RestTemplate restTemplate;

    public TaskService(TaskRepository taskRepository,
                       UserService userService,
                       RestTemplate restTemplate) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void create(Task task) {
        log.info("Start creating task for teacher with ID: {}", task.getTeacher().getId());
        var exist = taskRepository.existsByTitleAndTeacherId(task.getTitle(), task.getTeacher().getId());

        if (exist) {
            throw new TaskExistException(
                    String.format(TASK_EXIST, task.getTitle(), task.getTeacher().getName())
            );
        }

        taskRepository.save(task);
        log.info("Successfully created task for teacher with ID: {}", task.getTeacher().getId());
    }

    public TaskGenerateDto process(User user, Task task, TaskProcessCreateDto request) {
        log.info("Start process task '{}' for user with Telegram ID: {}", task.getTitle(), user.getTelegramId());

        TaskGenerateCreateDto taskGenerateDto = new TaskGenerateCreateDto(
                user.getTelegramId(),
                task.getTitle(),
                task.getTeacher().getName(),
                task.getTeacher().getSubject().getName(),
                task.getIdentifier(),
                request.arguments()
        );

        TaskGenerateDto processResponse;

        try {
            log.info("Attempting to send request to task generate service for user with Telegram ID: {}", user.getTelegramId());
            processResponse = restTemplate.postForObject("http://127.0.0.1:8083/tasks/generate", taskGenerateDto, TaskGenerateDto.class);
        } catch (HttpServerErrorException e) {
            throw new TaskProcessException(e.getMessage());
        }

        log.info("Successfully processed task '{}' for telegram ID: {}", task.getTitle(), user.getTelegramId());
        return processResponse;
    }

    public List<Task> getByTeacher(Long teacherId) {
        log.info("Attempting to fetch all tasks by teacher ID: {}", teacherId);
        return taskRepository.findByTeacherId(teacherId);
    }

    public Task getTaskById(Long taskId) {
        log.info("Attempting to fetch task by ID: {}", taskId);
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format(TASK_NOT_FOUND, taskId))
                );
    }

    @Transactional
    public void delete(Long taskId) {
        log.info("Start deleting task with ID: {}", taskId);
        boolean existTask = taskRepository.existsById(taskId);

        if (!existTask) {
            throw new TaskNotFoundException(
                    String.format(TASK_NOT_FOUND, taskId)
            );
        }

        taskRepository.deleteById(taskId);
        log.info("Successfully deleted task with ID: {}", taskId);
    }

    @Transactional
    public void update(Long id, TaskUpdateDto request) {
        log.info("Start updating task with ID: {}", id);

        Task task = getTaskById(id);

        if (request.title() != null && !request.title().isEmpty()) {
            task.setTitle(request.title());
        }

        if (request.description() != null && !request.description().isEmpty()) {
            task.setDescription(request.description());
        }

        if (request.identifier() != null && !request.identifier().isEmpty()) {
            task.setIdentifier(request.identifier());
        }

        if (request.price() != null) {
            task.setPrice(request.price());
        }

        if (request.discount() != null) {
            task.setDiscount(request.discount());
        }

        if (request.type() != null && !request.type().isEmpty()) {
            task.setType(resolveType(request.type()));
        }

        if (request.developerId() != null) {
            User developer = userService.findById(request.developerId());

            boolean hasRequiredRole = developer.getRoles()
                    .stream()
                    .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_DEVELOPER"));

            log.info("Checking developer has required 'ROLE_ADMIN' or 'ROLE_DEVELOPER' roles");
            if (!hasRequiredRole) {
                throw new TaskDeveloperNotValidException(
                        String.format(DEVELOPER_NOT_VALID, developer.getTelegramId())
                );
            }

            task.setDeveloper(developer);
        }

        task.setVisible(request.visible());
        task.setAutoGenerate(request.autoGenerate());

        taskRepository.save(task);
        log.info("Successfully updated task with ID: {}", task.getId());
    }

    private TaskType resolveType(String type) {
        try {
            return TaskType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }
}
