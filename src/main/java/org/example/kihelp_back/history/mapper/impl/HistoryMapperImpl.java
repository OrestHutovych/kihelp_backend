package org.example.kihelp_back.history.mapper.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.mapper.HistoryMapper;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryMapperImpl implements HistoryMapper {
    private final IdEncoderApiRepository idEncoderApiRepository;

    public HistoryMapperImpl(IdEncoderApiRepository idEncoderApiRepository) {
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

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
          history.getUser().getCourseNumber(),
          history.getUser().getCreatedAt().toString()
        );

        return new TaskDeveloperDto(
                encodeHistoryId(history.getId()),
                history.getTask().getTeacher().getSubject().getName(),
                history.getTask().getTeacher().getName(),
                history.getTask().getTitle(),
                history.getArguments(),
                history.getCreatedAt().toString(),
                userDto
        );
    }

    private String encodeHistoryId(Long id){
        return idEncoderApiRepository.findEncoderByName("history").encode(List.of(id));
    }
}
