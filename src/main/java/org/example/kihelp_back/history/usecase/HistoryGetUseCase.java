package org.example.kihelp_back.history.usecase;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;

import java.util.List;

public interface HistoryGetUseCase {
    List<HistoryDto> getHistoriesByUserTelegramId(String telegramId);
    List<TaskDeveloperDto> getTaskInProgressByDeveloperId();
    boolean detectResellerActivity(String telegramId, Long taskId);
}
