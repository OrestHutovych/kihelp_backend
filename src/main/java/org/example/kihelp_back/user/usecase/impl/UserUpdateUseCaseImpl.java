package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.dto.UserBanRequest;
import org.example.kihelp_back.user.dto.UserToggleRoleRequest;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserUpdateUseCaseImpl implements UserUpdateUseCase {
    private final UserService userService;

    public UserUpdateUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void changeBanValueByUser(Long userId, UserBanRequest request) {
        userService.changeBan(userId, request.value());
    }

    @Override
    public void toggleRole(String telegramId, UserToggleRoleRequest request) {
        userService.changeRole(telegramId, request.name());
    }
}
