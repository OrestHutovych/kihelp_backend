package org.example.kihelp_back.history.mapper.impl;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.mapper.HistoryToHistoryDtoMapper;
import org.example.kihelp_back.history.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryToHistoryDtoMapperImpl implements HistoryToHistoryDtoMapper {

    @Override
    public HistoryDto map(History history) {
        return new HistoryDto(
          history.getTask().getTeacher().getSubject().getName(),
          history.getTask().getTitle(),
          history.getLink(),
          history.getCreatedAt()
        );
    }
}
