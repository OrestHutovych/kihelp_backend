package org.example.kihelp_back.history.usecase;

import org.example.kihelp_back.history.dto.HistoryDto;

import java.util.List;

public interface HistoryGetUseCase {
    List<HistoryDto> getHistoriesByUserTelegramId(String telegramId);
}
