package org.example.kihelp_back.task.service;

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

import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

import static org.example.kihelp_back.task.util.TaskErrorMessage.*;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final RestTemplate restTemplate;

    private static final String TASK_GENERATE_URL = "http://127.0.0.1:8083/tasks/generate";

    public TaskService(TaskRepository taskRepository,
                       UserService userService,
                       RestTemplate restTemplate) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void create(Task task) {
        boolean exist = taskRepository.existsByTitleAndTeacherId(task.getTitle(), task.getTeacher().getId());

        if (exist) {
            throw new TaskExistException(
                    String.format(TASK_EXIST, task.getTitle(), task.getTeacher().getName())
            );
        }

        taskRepository.save(task);
    }

    public TaskGenerateDto process(User user, Task task, TaskProcessCreateDto request) {
        final TaskGenerateCreateDto taskGenerateDto = new TaskGenerateCreateDto(
                user.getTelegramId(),
                task.getTitle(),
                task.getTeacher().getName(),
                task.getTeacher().getSubject().getName(),
                task.getIdentifier(),
                request.arguments()
        );

        try {
            final TaskGenerateDto response = restTemplate.postForObject(
                    TASK_GENERATE_URL, taskGenerateDto, TaskGenerateDto.class);
            if (response == null) {
                throw new TaskProcessException(TASK_GENERATE_RESPONSE_NULL);
            }
            return response;
        } catch (HttpServerErrorException e) {
            throw new TaskProcessException(e.getMessage());
        }
    }

    public List<Task> getByTeacher(Long teacherId) {
        return taskRepository.findByTeacherId(teacherId);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(
                        String.format(TASK_NOT_FOUND, taskId))
                );
    }

    @Transactional
    public void delete(Long taskId) {
        boolean existTask = taskRepository.existsById(taskId);

        if (!existTask) {
            throw new TaskNotFoundException(
                    String.format(TASK_NOT_FOUND, taskId)
            );
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void update(Long id, TaskUpdateDto request) {
        Task task = getTaskById(id);

        updateIfNotBlank(request.title(), task::setTitle);
        updateIfNotBlank(request.description(), task::setDescription);
        updateIfNotBlank(request.identifier(), task::setIdentifier);

       if(request.price() != null) {
           task.setPrice(request.price());
       }

       if(request.discount() != null) {
           task.setDiscount(request.discount());
       }

       updateIfNotBlank(request.type(), value -> task.setType(resolveType(value)));
       updateIfNotBlank(request.developerTelegramId(), developerTelegramId -> {
           User developer = userService.hasDeveloperOrAdminRole(developerTelegramId);
           task.setDeveloper(developer);
       });

       task.setVisible(request.visible());
       task.setAutoGenerate(request.autoGenerate());

       taskRepository.save(task);
    }

    private void updateIfNotBlank(String value, Consumer<String> setter) {
        if (value != null && !value.trim().isEmpty()) {
            setter.accept(value);
        }
    }

    private TaskType resolveType(String type) {
        try {
            return TaskType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }
}
