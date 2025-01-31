package org.example.kihelp_back.history.mapper;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.model.History;

public interface HistoryMapper {
    HistoryDto toHistoryDto(History history);
    TaskDeveloperDto toTaskDeveloperDto(History history);
}
