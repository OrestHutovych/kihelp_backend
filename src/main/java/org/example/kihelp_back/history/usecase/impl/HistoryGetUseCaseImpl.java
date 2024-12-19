package org.example.kihelp_back.history.usecase.impl;

import org.example.kihelp_back.history.dto.HistoryResponse;
import org.example.kihelp_back.history.mapper.HistoryToHistoryResponseMapper;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        var histories = historyService.getHistoryByUser(user.getTelegramId());
        var historiesResponse = histories.stream()
                .map(historyToHistoryResponseMapper::map)
                .toList();

        return historiesResponse;
    }

    @Override
    public List<HistoryResponse> getHistoriesByUserTelegramId(String telegramId) {
        var histories = historyService.getHistoryByUser(telegramId);
        var historiesResponse = histories.stream()
                .map(historyToHistoryResponseMapper::map)
                .toList();

        return historiesResponse;
    }
}
