package org.example.kihelp_back.history.mapper.impl;

import org.example.kihelp_back.history.dto.HistoryResponse;
import org.example.kihelp_back.history.mapper.HistoryToHistoryResponseMapper;
import org.example.kihelp_back.history.model.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryToHistoryResponseMapperImpl implements HistoryToHistoryResponseMapper {

    @Override
    public HistoryResponse map(History history) {
        return new HistoryResponse(
          history.getId(),
          history.getTask().getTeacher().getSubject().getName(),
          history.getTask().getTitle(),
          history.getLink(),
          history.getCreatedTimeStamp()
        );
    }
}
