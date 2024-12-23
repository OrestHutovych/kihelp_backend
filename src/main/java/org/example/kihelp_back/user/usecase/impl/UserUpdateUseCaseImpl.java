package org.example.kihelp_back.user.usecase.impl;

import org.example.kihelp_back.user.dto.UserToggleBanDto;
import org.example.kihelp_back.user.dto.UserToggleRoleDto;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateUseCaseImpl implements UserUpdateUseCase {
    private final UserService userService;

    public UserUpdateUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void changeBanValueByUser(String telegramId, UserToggleBanDto request) {
        userService.changeBan(telegramId, request.value());
    }

    @Override
    public void toggleRole(String telegramId, UserToggleRoleDto request) {
        userService.changeRole(telegramId, request.roleName());
    }
}
