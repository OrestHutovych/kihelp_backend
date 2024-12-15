package org.example.kihelp_back.task.mapper;

import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.global.mapper.MapperV3;
import org.example.kihelp_back.global.mapper.MapperV4;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.dto.TaskRequest;
import org.example.kihelp_back.teacher.model.Teacher;
import org.example.kihelp_back.user.model.User;

import java.util.List;

public interface TaskRequestToTaskResponse extends MapperV4<Task, TaskRequest, Teacher, List<Argument>, User>
{
}
