package org.example.kihelp_back.history.usecase;

import org.example.kihelp_back.history.dto.HistorySaveFileDto;

public interface HistoryUpdateUseCase {
    void saveFileInHistory(String id, HistorySaveFileDto file);
}
