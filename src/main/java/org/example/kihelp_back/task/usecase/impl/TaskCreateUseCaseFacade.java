package org.example.kihelp_back.task.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.task.exception.TaskDeveloperNotValidException;
import org.example.kihelp_back.task.mapper.TaskCreateDtoToTaskResponse;
import org.example.kihelp_back.task.dto.TaskCreateDto;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.service.TaskService;
import org.example.kihelp_back.task.usecase.TaskCreateUseCase;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.DEVELOPER_NOT_VALID;
import static org.example.kihelp_back.task.util.ErrorMessage.IDENTIFIER_BLANK_NOT_VALID;

@Component
@Slf4j
public class TaskCreateUseCaseFacade implements TaskCreateUseCase {
    private final TaskService taskService;
    private final TeacherService teacherService;
    private final ArgumentService argumentService;
    private final UserService userService;
    private final TaskCreateDtoToTaskResponse taskCreateDtoToTaskResponse;

    public TaskCreateUseCaseFacade(TaskService taskService,
                                   TeacherService teacherService,
                                   TaskCreateDtoToTaskResponse taskCreateDtoToTaskResponse,
                                   ArgumentService argumentService,
                                   UserService userService) {
        this.taskService = taskService;
        this.teacherService = teacherService;
        this.taskCreateDtoToTaskResponse = taskCreateDtoToTaskResponse;
        this.argumentService = argumentService;
        this.userService = userService;
    }

    @Override
    public void createTask(TaskCreateDto taskRequest) {
        log.info("Checking for inclusion autoGenerate mode in TaskCreateDto");
        if (taskRequest.autoGenerate()) {
            String identifier = taskRequest.identifier();

            if (identifier == null || identifier.isBlank()) {
                throw new IllegalArgumentException(IDENTIFIER_BLANK_NOT_VALID);
            }
        }

        User developer = userService.findById(taskRequest.developerId());
        boolean hasRequiredRole = developer.getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals("ROLE_ADMIN") || r.getName().equals("ROLE_DEVELOPER"));

        log.info("Checking developer has required 'ROLE_ADMIN' or 'ROLE_DEVELOPER' roles");
        if (!hasRequiredRole) {
            throw new TaskDeveloperNotValidException(
                    String.format(DEVELOPER_NOT_VALID, taskRequest.developerId())
            );
        }

        Teacher teacher = teacherService.getTeacherById(taskRequest.teacherId());
        List<Argument> arguments = taskRequest.args()
                .stream()
                .map(argumentService::getById)
                .toList();

        log.info("Attempting to map TaskCreateDto '{}' to Task", taskRequest.title());
        Task task = taskCreateDtoToTaskResponse.map(taskRequest, teacher, arguments, developer);

        taskService.create(task);
    }
}
