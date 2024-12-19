package org.example.kihelp_back.history.service.impl;

import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.example.kihelp_back.history.service.HistoryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public List<History> getHistoryByUser(String telegramId) {
        return historyRepository.findAllByUserTelegramId(telegramId);
    }
}
