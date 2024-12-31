package org.example.kihelp_back.history.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.mapper.HistoryToHistoryDtoMapper;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class HistoryGetUseCaseFacade implements HistoryGetUseCase {
    private final HistoryService historyService;
    private final HistoryToHistoryDtoMapper historyToHistoryDtoMapper;

    public HistoryGetUseCaseFacade(HistoryService historyService,
                                   HistoryToHistoryDtoMapper historyToHistoryDtoMapper) {
        this.historyService = historyService;
        this.historyToHistoryDtoMapper = historyToHistoryDtoMapper;
    }

    @Override
    public List<HistoryDto> getHistoriesByUserTelegramId(String telegramId) {
        List<History> histories = historyService.getHistoryByUser(telegramId);

       log.info("Attempting to map History {} to HistoryDto and return it", histories.size());
        return histories.stream()
                .map(historyToHistoryDtoMapper::map)
                .toList();
    }
}
