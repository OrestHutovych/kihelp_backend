package org.example.kihelp_back.history.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.dto.HistoryResponse;
import org.example.kihelp_back.history.mapper.HistoryToHistoryResponseMapper;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HistoryGetUseCaseImpl implements HistoryGetUseCase {
    private final HistoryService historyService;
    private final HistoryToHistoryResponseMapper historyToHistoryResponseMapper;
    private final UserService userService;

    public HistoryGetUseCaseImpl(HistoryService historyService,
                                 HistoryToHistoryResponseMapper historyToHistoryResponseMapper,
                                 UserService userService) {
        this.historyService = historyService;
        this.historyToHistoryResponseMapper = historyToHistoryResponseMapper;
        this.userService = userService;
    }

    @Override
    public List<HistoryResponse> getHistoriesByUser() {
        var user = userService.findByJwt();

        log.info("Attempting to find histories by user Telegram ID: {}", user.getTelegramId());
        var histories = historyService.getHistoryByUser(user.getTelegramId());

        log.info("Mapping histories {} to histories response.", histories.size());
        var historiesResponse = histories.stream()
                .map(historyToHistoryResponseMapper::map)
                .toList();
        log.info("Successfully mapped histories response {}.", historiesResponse.size());

        return historiesResponse;
    }

    @Override
    public List<HistoryResponse> getHistoriesByUserTelegramId(String telegramId) {
        log.info("Attempting to find histories by user Telegram ID: {}", telegramId);
        var histories = historyService.getHistoryByUser(telegramId);

        log.info("Mapping histories {} to histories response.", histories.size());
        var historiesResponse = histories.stream()
                .map(historyToHistoryResponseMapper::map)
                .toList();
        log.info("Successfully mapped histories response {}.", historiesResponse.size());

        return historiesResponse;
    }
}
