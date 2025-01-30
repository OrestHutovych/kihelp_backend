package org.example.kihelp_back.user.usecase.impl;

import org.example.kihelp_back.user.dto.UserDto;
import org.example.kihelp_back.user.mapper.UserMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGetUseCaseFacade implements UserGetUseCase {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserGetUseCaseFacade(UserService userService,
                                UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto findByUserTelegramId(String telegramId) {
        User targetUser = userService.findByTelegramId(telegramId);

        return userMapper.toUserDto(targetUser);
    }

    @Override
    public List<UserDto> findByUserRole(String roleName) {
        List<User> users = userService.getByRole(roleName);

        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
