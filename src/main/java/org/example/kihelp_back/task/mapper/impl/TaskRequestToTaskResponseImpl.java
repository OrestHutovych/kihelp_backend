package org.example.kihelp_back.task.mapper.impl;

import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.task.exception.TypeNotValidException;
import org.example.kihelp_back.task.mapper.TaskRequestToTaskResponse;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskRequest;
import org.example.kihelp_back.task.model.Type;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.user.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import static org.example.kihelp_back.task.util.ErrorMessage.TYPE_NOT_VALID;

@Component
public class TaskRequestToTaskResponseImpl implements TaskRequestToTaskResponse {


    @Override
    public Task map(TaskRequest taskRequest, Teacher teacher, List<Argument> arguments, User developer) {
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setIdentifier(taskRequest.identifier());
        task.setPrice(taskRequest.price());
        task.setDiscount(0.0);
        task.setVisible(false);
        task.setType(resolveType(taskRequest.type()));
        task.setDeveloper(developer);
        task.setAutoGenerate(taskRequest.autoGenerate());
        task.setCreatedTimeStamp(Instant.now());
        task.setTeacher(teacher);
        task.setArguments(arguments);
        return task;
    }

    private Type resolveType(String type) {
        try {
            return Type.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new TypeNotValidException(String.format(TYPE_NOT_VALID, type));
        }
    }
}
