package org.example.kihelp_back.history.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.history.dto.HistorySaveFileApiDto;
import org.example.kihelp_back.history.dto.HistorySaveFileDto;
import org.example.kihelp_back.history.exception.HistoryNotFoundException;
import org.example.kihelp_back.history.exception.TaskConvertException;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryUpdateUseCase;
import org.example.kihelp_back.task.dto.TaskGenerateDto;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.example.kihelp_back.history.util.HistoryErrorMessage.HISTORY_DEV_ERR;
import static org.example.kihelp_back.history.util.HistoryErrorMessage.HISTORY_NOT_IN_PROGRESS;

@Component
public class HistoryUpdateUseCaseFacade implements HistoryUpdateUseCase {
    private final static String CONVERT_LINK_URL = "http://localhost:8083/api/v1/files/upload";

    private final HistoryService historyService;
    private final IdEncoderApiRepository idEncoderApiRepository;
    private final RestTemplate restTemplate;
    private final TelegramBotService telegramBotService;
    private final UserService userService;

    public HistoryUpdateUseCaseFacade(HistoryService historyService,
                                      IdEncoderApiRepository idEncoderApiRepository,
                                      RestTemplate restTemplate,
                                      TelegramBotService telegramBotService,
                                      UserService userService) {
        this.historyService = historyService;
        this.idEncoderApiRepository = idEncoderApiRepository;
        this.restTemplate = restTemplate;
        this.telegramBotService = telegramBotService;
        this.userService = userService;
    }

    @Override
    public void saveFileInHistory(String encodedId, HistorySaveFileDto fileDto) {
        User user = userService.findByJwt();
        Long historyId = idEncoderApiRepository.findEncoderByName("history").decode(encodedId).get(0);
        History history = historyService.getHistoryById(historyId);

        if (!hasRole(user, "ROLE_DEVELOPER") && !hasRole(user, "ROLE_ADMIN")) {
            throw new IllegalArgumentException(HISTORY_DEV_ERR);
        }

        if (!HistoryStatus.IN_PROGRESS.equals(history.getStatus())) {
            throw new HistoryNotFoundException(HISTORY_NOT_IN_PROGRESS);
        }

        TaskGenerateDto taskResult = convertFile(fileDto, history);
        History updatedHistory = historyService.saveFileInDeveloperInProgresTask(history, taskResult);
        telegramBotService.sendMessageToUserChatAboutCompletedTask(updatedHistory);
    }

    private TaskGenerateDto convertFile(HistorySaveFileDto fileDto, History history) {
        try {
            String fileEncoded = Base64.getEncoder().encodeToString(fileDto.file().getBytes());
            String fileName = fileDto.file().getOriginalFilename();
            List<String> args = parseStringArgToList(history.getArguments());

            HistorySaveFileApiDto apiDto = new HistorySaveFileApiDto(
                    history.getUser().getTelegramId(),
                    history.getTask().getTeacher().getSubject().getName(),
                    history.getTask().getTeacher().getName(),
                    history.getTask().getTitle(),
                    args,
                    fileEncoded,
                    fileName
            );

            return restTemplate.postForObject(CONVERT_LINK_URL, apiDto, TaskGenerateDto.class);
        } catch (HttpServerErrorException | IOException e) {
            throw new TaskConvertException(e.getMessage());
        }
    }

    private List<String> parseStringArgToList(String args) {
        if (args == null || args.length() < 2) {
            return Collections.emptyList();
        }
        String trimmed = args.trim();
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1);
        }
        if (trimmed.isEmpty()) {
            return Collections.emptyList();
        }
        return List.of(trimmed.split(",\\s*"));
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> roleName.equals(role.getName()));
    }
}
