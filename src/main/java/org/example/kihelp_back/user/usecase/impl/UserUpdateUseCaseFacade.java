package org.example.kihelp_back.user.usecase.impl;

import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateUseCaseFacade implements UserUpdateUseCase {
    private final UserService userService;

    public UserUpdateUseCaseFacade(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void changeBanValueByUser(String telegramId, boolean value) {
        userService.changeBan(telegramId, value);
    }

    @Override
    public void toggleRole(String telegramId, String roleName) {
        userService.changeRole(telegramId, roleName);
    }
}
