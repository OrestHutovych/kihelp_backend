package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.task.exception.TaskDeveloperNotValidException;
import org.example.kihelp_back.task.mapper.TaskRequestToTaskResponse;
import org.example.kihelp_back.task.dto.TaskRequest;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.example.kihelp_back.task.util.ErrorMessage.DEVELOPER_NOT_VALID;
import static org.example.kihelp_back.task.util.ErrorMessage.IDENTIFIER_BLANK_NOT_VALID;

@Component
@Slf4j
public class TaskCreateUseCaseImpl implements TaskCreateUseCase {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final ArgumentService argumentService;
    private final UserService userService;
    private final TaskRequestToTaskResponse taskRequestToTaskResponse;

    public TaskCreateUseCaseImpl(TaskService taskService,
                                 TeacherService teacherService,
                                 TaskRequestToTaskResponse taskRequestToTaskResponse,
                                 ArgumentService argumentService,
                                 UserService userService) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.taskRequestToTaskResponse = taskRequestToTaskResponse;
        this.argumentService = argumentService;
        this.userService = userService;
    }

    @Override
    public void createTask(TaskRequest taskRequest) {
        if (taskRequest.autoGenerate()) {
            log.debug("Auto-generation is enabled for the task.");

            String identifier = taskRequest.identifier();
            if (identifier == null || identifier.isBlank()) {
                log.warn("Identifier is null. Throwing IllegalArgumentException.");
                throw new IllegalArgumentException(IDENTIFIER_BLANK_NOT_VALID);
            }

            log.debug("Task identifier is valid: {}", identifier);
        }

        log.debug("Attempting to find developer with id '{}'", taskRequest.developerId());
        var developer = userService.findById(taskRequest.developerId());

        var roles = developer.getRoles()
                .stream()
                .filter(r -> r.getName().equals("ROLE_DEVELOPER") || r.getName().equals("ROLE_ADMIN"))
                .toList();

        if (roles.isEmpty()) {
            log.warn("Role is not equals Developer. Throwing TaskDeveloperNotValidException.");
            throw new TaskDeveloperNotValidException(
                    String.format(DEVELOPER_NOT_VALID, taskRequest.developerId())
            );
        }

        log.debug("Attempting to find teacher with id '{}'", taskRequest.teacherId());
        var teacher = teacherService.getTeacherById(taskRequest.teacherId());
        log.debug("Successfully found teacher: {}", teacher);

        log.debug("Attempting to find arguments by id: {}", taskRequest.args());
        var arguments = taskRequest.args()
                .stream()
                .map(argumentService::getById)
                .collect(Collectors.toList());
        log.debug("Successfully found arguments: {}", arguments);

        log.debug("Mapping TaskRequest {} with teacher {} and args {} to Task entity.",
                taskRequest,
                teacher,
                arguments
        );

        var task = taskRequestToTaskResponse.map(taskRequest, teacher, arguments, developer);
        log.debug("Mapped Task entity: {}", task);

        taskService.create(task);
    }
}
