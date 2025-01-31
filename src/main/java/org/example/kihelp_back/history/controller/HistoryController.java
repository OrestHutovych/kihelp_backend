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
@RequestMapping("/api/v1/histories")
public class HistoryController {
    private final HistoryGetUseCase historyGetUseCase;

    public HistoryController(HistoryGetUseCase historyGetUseCase) {
        this.historyGetUseCase = historyGetUseCase;
    }

    @GetMapping("/user/{telegram_id}")
    public List<HistoryDto> getHistoriesByUserTelegramId(@PathVariable("telegram_id") String telegramId){
        return historyGetUseCase.getHistoriesByUserTelegramId(telegramId);
    }

    @GetMapping("/user/in-progress")
    public List<TaskDeveloperDto> getTaskInProgresByDeveloper(){
        return historyGetUseCase.getTaskInProgressByDeveloper();
    }

    @GetMapping("/task/{task_id}/user/{telegram_id}/reseller-check")
    public boolean resellerCheck(@PathVariable("telegram_id") String telegramId,
                                 @PathVariable("task_id") Long taskId){
        return historyGetUseCase.detectResellerActivity(telegramId, taskId);
    }
}
