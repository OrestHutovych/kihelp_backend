package org.example.kihelp_back.history.controller;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.HistorySaveFileDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.example.kihelp_back.history.usecase.HistoryUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/histories")
public class HistoryController {
    private final HistoryGetUseCase historyGetUseCase;
    private final HistoryUpdateUseCase historyUpdateUseCase;

    public HistoryController(HistoryGetUseCase historyGetUseCase,
                             HistoryUpdateUseCase historyUpdateUseCase) {
        this.historyGetUseCase = historyGetUseCase;
        this.historyUpdateUseCase = historyUpdateUseCase;
    }

    @GetMapping("/user/{telegram_id}")
    public List<HistoryDto> getHistoriesByUserTelegramId(@PathVariable("telegram_id") String telegramId){
        return historyGetUseCase.getHistoriesByUserTelegramId(telegramId);
    }

    @GetMapping("/developer/in-progress")
    public List<TaskDeveloperDto> getTaskInProgresByDeveloper(){
        return historyGetUseCase.getTaskInProgressByDeveloper();
    }

    @PatchMapping("/history/{history_id}/developer/save-file")
    public void saveFileInHistory(@PathVariable("history_id") String id, @ModelAttribute HistorySaveFileDto file){
        historyUpdateUseCase.saveFileInHistory(id, file);
    }

    @GetMapping("/task/{task_id}/user/{telegram_id}/reseller-check")
    public boolean resellerCheck(@PathVariable("telegram_id") String telegramId,
                                 @PathVariable("task_id") String taskId){
        return historyGetUseCase.detectResellerActivity(telegramId, taskId);
    }
}
