package org.example.kihelp_back.history.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.history.dto.HistoryDto;
import org.example.kihelp_back.history.dto.TaskDeveloperDto;
import org.example.kihelp_back.history.mapper.HistoryToHistoryDtoMapper;
import org.example.kihelp_back.history.mapper.HistoryToTaskDeveloperDtoMapper;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryGetUseCase;
import org.example.kihelp_back.user.exception.UserRoleNotValidException;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.kihelp_back.history.util.ErrorMessage.USER_ROLE_INVALID;

@Component
@Slf4j
public class HistoryGetUseCaseFacade implements HistoryGetUseCase {
    private final HistoryService historyService;
    private final HistoryToHistoryDtoMapper historyToHistoryDtoMapper;
    private final HistoryToTaskDeveloperDtoMapper historyToTaskDeveloperDtoMapper;
    private final UserService userService;

    public HistoryGetUseCaseFacade(HistoryService historyService,
                                   HistoryToHistoryDtoMapper historyToHistoryDtoMapper,
                                   HistoryToTaskDeveloperDtoMapper historyToTaskDeveloperDtoMapper,
                                   UserService userService) {
        this.historyService = historyService;
        this.historyToHistoryDtoMapper = historyToHistoryDtoMapper;
        this.historyToTaskDeveloperDtoMapper = historyToTaskDeveloperDtoMapper;
        this.userService = userService;
    }

    @Override
    public List<HistoryDto> getHistoriesByUserTelegramId(String telegramId) {
        List<History> histories = historyService.getHistoryByUser(telegramId);

       log.info("Attempting to map History {} to HistoryDto and return it", histories.size());
        return histories.stream()
                .map(historyToHistoryDtoMapper::map)
                .toList();
    }

    @Override
    public List<TaskDeveloperDto> getTaskInProgressByDeveloperId() {
        User developer = userService.findByJwt();

        if(developer.getRoles().stream().anyMatch(role -> "ROLE_DEVELOPER".equals(role.getName()))) {
            List<History> historiesForDeveloper = historyService.getHistoryInProgresByDeveloper(developer.getTelegramId());

            return historiesForDeveloper.stream()
                    .map(historyToTaskDeveloperDtoMapper::map)
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
