package org.example.kihelp_back.task.mapper;

import org.example.kihelp_back.global.MapperV2;
import org.example.kihelp_back.task.model.Task;
import org.example.kihelp_back.task.model.TaskRequest;
import org.example.kihelp_back.teacher.model.Teacher;

public interface TaskRequestToTaskResponse extends MapperV2<Task, TaskRequest, Teacher> {
}
