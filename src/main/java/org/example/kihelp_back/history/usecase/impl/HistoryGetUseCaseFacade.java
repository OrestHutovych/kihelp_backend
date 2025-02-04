package org.example.kihelp_back.history.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
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

import static org.example.kihelp_back.history.util.HistoryErrorMessage.HISTORY_NOT_USER;
import static org.example.kihelp_back.history.util.HistoryErrorMessage.USER_ROLE_INVALID;

@Component
public class HistoryGetUseCaseFacade implements HistoryGetUseCase {
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;
    private final UserService userService;
    private final IdEncoderApiRepository idEncoderApiRepository;

    public HistoryGetUseCaseFacade(HistoryService historyService,
                                   HistoryMapper historyMapper,
                                   UserService userService, IdEncoderApiRepository idEncoderApiRepository) {
        this.historyService = historyService;
        this.historyMapper = historyMapper;
        this.userService = userService;
        this.idEncoderApiRepository = idEncoderApiRepository;
    }

    @Override
    public List<HistoryDto> getHistoriesByUserTelegramId(String telegramId) {
        User sender = userService.findByJwt();
        User targetUser = userService.findByTelegramId(telegramId);

        if(!hasRole(sender, "ROLE_ADMIN") || !sender.equals(targetUser)) {
            throw new IllegalArgumentException(HISTORY_NOT_USER);
        }

        List<History> histories = historyService.getHistoryByUser(telegramId);

        return histories.stream()
                .map(historyMapper::toHistoryDto)
                .toList();
    }

    @Override
    public List<TaskDeveloperDto> getTaskInProgressByDeveloper() {
        User developer = userService.findByJwt();

        if(developer.getRoles().stream().anyMatch(role -> ("ROLE_DEVELOPER".equals(role.getName())) || ("ROLE_ADMIN".equals(role.getName())))) {
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
    public boolean detectResellerActivity(String telegramId, String taskId) {
        Long decodedTaskId = idEncoderApiRepository.findEncoderByName("task").decode(taskId).get(0);
        return historyService.detectResellerActivity(telegramId, decodedTaskId);
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }
}
