package org.example.kihelp_back.history.mapper.impl;

import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.mapper.HistoryToTaskDeveloperDtoMapper;
import org.example.kihelp_back.history.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryToTaskDeveloperDtoMapperImpl implements HistoryToTaskDeveloperDtoMapper {

    @Override
    public TaskDeveloperDto map(History history) {
        return new TaskDeveloperDto(
                history.getId(),
                history.getUser().getUsername(),
                history.getUser().getTelegramId(),
                history.getTask().getTeacher().getSubject().getName(),
                history.getTask().getTeacher().getName(),
                history.getTask().getTitle(),
                history.getArguments(),
                history.getCreatedAt()
        );
    }
}
