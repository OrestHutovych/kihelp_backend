package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.subject.dto.SubjectDto;
import org.example.kihelp_back.task.mapper.TaskToTaskDtoMapper;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskDto;
import org.example.kihelp_back.teacher.dto.TeacherDto;
import org.example.kihelp_back.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoMapperImpl implements TaskToTaskDtoMapper {

    @Override
    public TaskDto map(Task task) {
        if(task == null) {
            return null;
        }

        UserDto developer = new UserDto(
                task.getDeveloper().getUsername(),
                task.getDeveloper().getTelegramId(),
                task.getDeveloper().getCreatedAt().toString()
        );

        SubjectDto subject = new SubjectDto(
                task.getTeacher().getSubject().getId(),
                task.getTeacher().getSubject().getName(),
                task.getTeacher().getSubject().getCourseNumber()
        );

        TeacherDto teacher = new TeacherDto(
                task.getTeacher().getId(),
                task.getTeacher().getName(),
                subject
        );

        return new TaskDto(
              task.getId(),
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
              task.getArguments(),
              teacher
        );
    }
}
