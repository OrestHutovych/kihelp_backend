package org.example.kihelp_back.history.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void save(History history) {
        log.info("Start saving history for user with Telegram ID: {}", history.getUser().getTelegramId());
        historyRepository.save(history);
        log.info("Successfully saved history for user with Telegram ID: {}", history.getUser().getTelegramId());
    }

    public List<History> getHistoryByUser(String telegramId) {
        log.info("Attempting to fetch history for user with Telegram ID: {}", telegramId);
        return historyRepository.findAllByUserTelegramId(telegramId);
    }
}
