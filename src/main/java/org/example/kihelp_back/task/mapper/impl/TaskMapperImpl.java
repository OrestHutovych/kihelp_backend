package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.argument.dto.ArgumentDto;
import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.service.ArgumentService;
import org.example.kihelp_back.argument.usecase.ArgumentGetUseCase;
import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.task.dto.TaskCreateDto;
import org.example.kihelp_back.task.dto.TaskDto;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.task.mapper.TaskMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.model.TaskType;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.teacher.mapper.TeacherMapper;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.teacher.service.TeacherService;
import org.example.kihelp_back.user.dto.UserDto;
import org.example.kihelp_back.user.mapper.UserMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.kihelp_back.task.util.TaskErrorMessage.ARGS_NOT_NULL;
import static org.example.kihelp_back.task.util.TaskErrorMessage.TYPE_NOT_VALID;

@Component
public class TaskMapperImpl implements TaskMapper {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final ArgumentService argumentService;
    private final ArgumentGetUseCase argumentGetUseCase;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public TaskMapperImpl(UserService userService,
                          UserMapper userMapper,
                          TeacherService teacherService,
                          TeacherMapper teacherMapper,
                          ArgumentService argumentService,
                          ArgumentGetUseCase argumentGetUseCase,
                          IdEncoderApiRepository idEncoderApiRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
        this.argumentService = argumentService;
        this.argumentGetUseCase = argumentGetUseCase;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public Task toEntity(TaskCreateDto taskCreateDto) {
        if(taskCreateDto == null) {
            return null;
        }

        User developer = userService.hasDeveloperOrAdminRole(taskCreateDto.developerTelegramId());
        Teacher teacher = teacherService.findTeacherById(decodeTeacherId(taskCreateDto.teacherId()));
        List<Argument> arguments = taskCreateDto.args()
                .stream()
                .map(argumentService::getById)
                .toList();

        if(arguments.isEmpty()) {
            throw new IllegalArgumentException(ARGS_NOT_NULL);
        }

        Task task = new Task();

        task.setTitle(taskCreateDto.title());
        task.setDescription(taskCreateDto.description());
        task.setIdentifier(taskCreateDto.identifier());
        task.setPrice(taskCreateDto.price());
        task.setType(resolveType(taskCreateDto.type()));
        task.setAutoGenerate(taskCreateDto.autoGenerate());
        task.setDeveloper(developer);
        task.setTeacher(teacher);
        task.setArguments(arguments);

        return task;
    }

    @Override
    public TaskDto toTaskDto(Task task) {
        if(task == null) {
            return null;
        }

        UserDto developer = userMapper.toUserDto(task.getDeveloper());
        TeacherDto teacher = teacherMapper.toTeacherDto(task.getTeacher());
        List<ArgumentDto> arguments = argumentGetUseCase.findArgumentsByTaskId(task.getId());

        return new TaskDto(
                encodeTaskId(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getIdentifier(),
                task.getPrice(),
                task.getDiscount(),
                task.isVisible(),
                task.getType().name(),
                developer,
                task.isAutoGenerate(),
                task.getCreatedAt().toString(),
                arguments,
                teacher
        );
    }

    private TaskType resolveType(String type) {
        try {
            return TaskType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }

    public Long decodeTeacherId(String teacherId) {
        return idEncoderApiRepository.findEncoderByName("teacher").decode(teacherId).get(0);
    }

    public String encodeTaskId(Long taskId) {
        return idEncoderApiRepository.findEncoderByName("task").encode(List.of(taskId));
    }
}
