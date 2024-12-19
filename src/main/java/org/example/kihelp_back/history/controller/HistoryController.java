package org.example.kihelp_back.history.controller;

import org.example.kihelp_back.history.dto.HistoryResponse;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/histories")
public class HistoryController {
    private final HistoryGetUseCase historyGetUseCase;

    public HistoryController(HistoryGetUseCase historyGetUseCase) {
        this.historyGetUseCase = historyGetUseCase;
    }

    @GetMapping("/history")
    public List<HistoryResponse> getHistoriesByUser(){
        return historyGetUseCase.getHistoriesByUser();
    }

    @GetMapping("/history/by/user/{telegram_id}")
    public List<HistoryResponse> getHistoriesByTelegramId(@PathVariable("telegram_id") String telegramId){
        return historyGetUseCase.getHistoriesByUserTelegramId(telegramId);
    }
}
