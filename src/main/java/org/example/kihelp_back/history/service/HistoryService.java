package org.example.kihelp_back.history.service;

import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void save(History history) {
        historyRepository.save(history);
    }

    public List<History> getHistoryByUser(String telegramId) {
        return historyRepository.findAllByUserTelegramId(telegramId);
    }

    public List<History> getHistoryInProgresByDeveloper(String developerTelegramId) {
        return historyRepository.findTaskInProgressByDeveloperTelegramId(developerTelegramId, HistoryStatus.IN_PROGRESS);
    }

    public boolean detectResellerActivity(String telegramId, Long taskId) {
        List<History> histories = historyRepository.findAllByTaskId(taskId);
        Set<String> uniqueArguments = new HashSet<>();

        for (History history : histories) {
            uniqueArguments.add(history.getArguments());
            if (uniqueArguments.size() >= 3) {
                return true;
            }
        }

        return false;
    }
}
