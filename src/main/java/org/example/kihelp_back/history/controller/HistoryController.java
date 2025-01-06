package org.example.kihelp_back.history.controller;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {
    private final HistoryGetUseCase historyGetUseCase;

    public HistoryController(HistoryGetUseCase historyGetUseCase) {
        this.historyGetUseCase = historyGetUseCase;
    }

    @GetMapping("/getHistoriesByTelegramId/{telegram_id}")
    public List<HistoryDto> getHistoriesByTelegramId(@PathVariable("telegram_id") String telegramId){
        return historyGetUseCase.getHistoriesByUserTelegramId(telegramId);
    }

    @GetMapping("/getTaskInProgresByDeveloper")
    public List<TaskDeveloperDto> getTaskInProgresByDeveloper(){
        return historyGetUseCase.getTaskInProgressByDeveloperId();
    }

    @GetMapping("/reseller-check/{telegram_id}/{task_id}")
    public boolean resellerCheck(@PathVariable("telegram_id") String telegramId, @PathVariable("task_id") Long taskId){
        return historyGetUseCase.detectResellerActivity(telegramId, taskId);
    }
}
