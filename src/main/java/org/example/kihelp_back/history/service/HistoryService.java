package org.example.kihelp_back.history.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.kihelp_back.history.exception.HistoryNotFoundException;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.example.kihelp_back.history.repository.HistoryRepository;
import org.example.kihelp_back.task.dto.TaskGenerateDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.kihelp_back.history.util.HistoryErrorMessage.HISTORY_NOT_FOUND;

@Component
public class HistoryService {
    private final HistoryRepository historyRepository;
    private AtomicInteger money;

    public HistoryService(HistoryRepository historyRepository, MeterRegistry meterRegistry) {
        this.historyRepository = historyRepository;
        this.money = new AtomicInteger();
        meterRegistry.gauge("totalEarnedMoney", money);
    }

    @Transactional
    public void save(History history) {
        historyRepository.save(history);
    }

    @Transactional
    public History saveFileInDeveloperInProgresTask(History history, TaskGenerateDto taskGenerateDto) {
        if(taskGenerateDto.link() != null && !taskGenerateDto.link().isEmpty()) {
            history.setLink(taskGenerateDto.link());
        }

        if(taskGenerateDto.fileName() != null && !taskGenerateDto.fileName().isEmpty()) {
            history.setName(taskGenerateDto.fileName());
        }

        history.setStatus(HistoryStatus.COMPLETED);

        return historyRepository.save(history);
    }

    public History getHistoryById(Long historyId) {
        return historyRepository.findById(historyId)
                .orElseThrow(() -> new HistoryNotFoundException(
                        String.format(HISTORY_NOT_FOUND, historyId)
                ));
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

    @Scheduled(fixedRate = 60000)
    public BigDecimal getEarnedMoney() {
        BigDecimal totalMoney = historyRepository.getTotalEarnedMoney();

        if(totalMoney != null) {
            money.set(totalMoney.intValue());
        }

        return totalMoney;
    }
}
