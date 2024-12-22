package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.mapper.UserToUserDtoMapper;
import org.example.kihelp_back.user.dto.UserDto;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserGetUseCaseImpl implements UserGetUseCase {
    private final UserService userService;
    private final UserToUserDtoMapper userToUserResponseMapper;

    public UserGetUseCaseImpl(UserService userService,
                              UserToUserDtoMapper userToUserResponseMapper) {
        this.userService = userService;
        this.userToUserResponseMapper = userToUserResponseMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        var users = userService.getAll();

        log.info("Attempting to map User to UserResponse");
        return users.stream()
                .map(userToUserResponseMapper::map)
                .toList();
    }

    @Override
    public List<UserDto> getUserByRole(String roleName) {
        var users = userService.getByRole(roleName);

        log.info("Attempting to map User to UserResponse");
        return users.stream()
                .map(userToUserResponseMapper::map)
                .toList();
    }

}
