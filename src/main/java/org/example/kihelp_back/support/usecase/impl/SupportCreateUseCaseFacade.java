package org.example.kihelp_back.support.usecase.impl;

import org.example.kihelp_back.global.service.TelegramBotService;
import org.example.kihelp_back.support.dto.SupportDto;
import org.example.kihelp_back.support.exception.SupportFileLimitException;
import org.example.kihelp_back.support.usecase.SupportCreateUseCase;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.springframework.stereotype.Component;
import static org.example.kihelp_back.support.util.ErrorMessage.FILE_LIMIT;

@Component
public class SupportCreateUseCaseFacade implements SupportCreateUseCase {
    private final TelegramBotService telegramBotService;
    private final UserService userService;

    public SupportCreateUseCaseFacade(TelegramBotService telegramBotService,
                                      UserService userService) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
    }


    @Override
    public void sendMessage(SupportDto supportDto) {
        if(supportDto.files().size() > 3){
            throw new SupportFileLimitException(FILE_LIMIT);
        }

        User user = userService.findByJwt();

        telegramBotService.supportMessageSentToAdmin(user, supportDto);
    }
}
