package org.example.kihelp_back.history.mapper.impl;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.mapper.HistoryMapper;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapperImpl implements HistoryMapper {

    @Override
    public HistoryDto toHistoryDto(History history) {
        if(history == null) {
            return null;
        }

        return new HistoryDto(
                history.getTask().getTeacher().getSubject().getName(),
                history.getTask().getTitle(),
                history.getLink(),
                history.getCreatedAt().toString()
        );
    }

    @Override
    public TaskDeveloperDto toTaskDeveloperDto(History history) {
        if(history == null) {
            return null;
        }

        UserDto userDto = new UserDto(
          history.getUser().getUsername(),
          history.getUser().getTelegramId(),
          history.getUser().getCreatedAt().toString()
        );

        return new TaskDeveloperDto(
                history.getId(),
                userDto,
                history.getTask().getTeacher().getSubject().getName(),
                history.getTask().getTeacher().getName(),
                history.getTask().getTitle(),
                history.getArguments(),
                history.getCreatedAt().toString()
        );
    }
}
