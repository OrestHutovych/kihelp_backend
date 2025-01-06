package org.example.kihelp_back.history.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<History> getHistoryInProgresByDeveloper(String developerTelegramId) {
        log.info("Attempting to fetch history for developer with Telegram ID: {} by status IN_PROGRES.", developerTelegramId);
        return historyRepository.findTaskInProgressByDeveloperTelegramId(developerTelegramId, HistoryStatus.IN_PROGRESS);
    }

    public boolean detectResellerActivity(String telegramId, Long taskId) {
        log.info("Start detecting user with Telegram ID: {} by reseller activity", telegramId);
        List<History> histories = historyRepository.findAllByTaskId(taskId);
        Set<String> uniqueArguments = new HashSet<>();

        for (History history : histories) {
            uniqueArguments.add(history.getArguments());
            if (uniqueArguments.size() >= 3) {
                log.info("Successfully detected reseller activity for user with Telegram ID: {} ", telegramId);
                return true;
            }
        }

        log.info("User with Telegram ID: {} passed the reseller activity check.", telegramId);
        return false;
    }
}
