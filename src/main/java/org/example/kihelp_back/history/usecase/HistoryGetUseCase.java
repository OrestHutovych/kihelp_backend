package org.example.kihelp_back.history.usecase;

import org.example.kihelp_back.history.dto.HistoryResponse;

import java.util.List;

public interface HistoryGetUseCase {
    List<HistoryResponse> getHistoriesByUser();
    List<HistoryResponse> getHistoriesByUserTelegramId(String telegramId);
}
