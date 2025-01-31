package org.example.kihelp_back.history.usecase;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;

import java.util.List;

public interface HistoryGetUseCase {
    List<HistoryDto> getHistoriesByUserTelegramId(String telegramId);
    List<TaskDeveloperDto> getTaskInProgressByDeveloper();
    boolean detectResellerActivity(String telegramId, String taskId);
}
