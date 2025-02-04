package org.example.kihelp_back.history.usecase.impl;

import org.example.kihelp_back.global.api.idencoder.IdEncoderApiRepository;
import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.history.dto.HistorySaveFileDto;
import org.example.kihelp_back.history.exception.TaskConvertException;
import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.service.HistoryService;
import org.example.kihelp_back.history.usecase.HistoryUpdateUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class HistoryUpdateUseCaseFacade implements HistoryUpdateUseCase {
    private final HistoryService historyService;
    private final IdEncoderApiRepository idEncoderApiRepository;
    private final RestTemplate restTemplate;

    private final static String CONVERT_LINK_URL = "http://localhost:8083/api/v2/save-file";
    private final TelegramBotService telegramBotService;

    public HistoryUpdateUseCaseFacade(HistoryService historyService,
                                      IdEncoderApiRepository idEncoderApiRepository,
                                      RestTemplate restTemplate, TelegramBotService telegramBotService) {
        this.historyService = historyService;
        this.idEncoderApiRepository = idEncoderApiRepository;
        this.restTemplate = restTemplate;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void saveFileInHistory(String id, HistorySaveFileDto file) {
        Long decodedHistoryId = idEncoderApiRepository.findEncoderByName("history").decode(id).get(0);
        String link;

        try {
            link = restTemplate.postForObject(
                    CONVERT_LINK_URL, file, String.class);
        } catch (HttpServerErrorException e) {
            throw new TaskConvertException(e.getMessage());
        }

        History updatedHistory = historyService.saveFileInDeveloperInProgresTask(decodedHistoryId, link);
        telegramBotService.sendMessageToUserChatAboutCompletedTask(updatedHistory);
    }
}
