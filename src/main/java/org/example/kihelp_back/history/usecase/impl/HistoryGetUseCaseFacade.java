package org.example.kihelp_back.history.usecase.impl;

import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.mapper.HistoryMapper;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.example.kihelp_back.user.exception.UserRoleNotValidException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.kihelp_back.history.util.HistoryErrorMessage.USER_ROLE_INVALID;

@Component
public class HistoryGetUseCaseFacade implements HistoryGetUseCase {
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;
    private final UserService userService;

    public HistoryGetUseCaseFacade(HistoryService historyService,
                                   HistoryMapper historyMapper,
                                   UserService userService) {
        this.historyService = historyService;
        this.historyMapper = historyMapper;
        this.userService = userService;
    }

    @Override
    public List<HistoryDto> getHistoriesByUserTelegramId(String telegramId) {
        List<History> histories = historyService.getHistoryByUser(telegramId);

        return histories.stream()
                .map(historyMapper::toHistoryDto)
                .toList();
    }

    @Override
    public List<TaskDeveloperDto> getTaskInProgressByDeveloper() {
        User developer = userService.findByJwt();

        if(developer.getRoles().stream().anyMatch(role -> "ROLE_DEVELOPER".equals(role.getName()))) {
            List<History> historiesForDeveloper = historyService.getHistoryInProgresByDeveloper(developer.getTelegramId());

            return historiesForDeveloper.stream()
                    .map(historyMapper::toTaskDeveloperDto)
                    .toList();
        }

        throw new UserRoleNotValidException(String.format(
                USER_ROLE_INVALID,
                developer.getTelegramId()
        ));
    }

    @Override
    public boolean detectResellerActivity(String telegramId, Long taskId) {
        return historyService.detectResellerActivity(telegramId, taskId);
    }
}
