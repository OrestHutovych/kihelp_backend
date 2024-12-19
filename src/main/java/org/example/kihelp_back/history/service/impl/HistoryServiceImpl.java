package org.example.kihelp_back.history.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.example.kihelp_back.history.service.HistoryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public void save(History history) {
        var savedHistory = historyRepository.save(history);
        log.info("Successfully saved history for user with Telegram ID: {}.", savedHistory.getUser().getTelegramId());
    }

    @Override
    public List<History> getHistoryByUser(String telegramId) {
        return historyRepository.findAllByUserTelegramId(telegramId);
    }
}
