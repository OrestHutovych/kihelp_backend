package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.mapper.UserToUserResponseMapper;
import org.example.kihelp_back.user.dto.UserResponse;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserGetUseCaseImpl implements UserGetUseCase {
    private final UserService userService;
    private final UserToUserResponseMapper userToUserResponseMapper;

    public UserGetUseCaseImpl(UserService userService,
                              UserToUserResponseMapper userToUserResponseMapper) {
        this.userService = userService;
        this.userToUserResponseMapper = userToUserResponseMapper;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        var users = userService.getAll();

        log.debug("Mapping {} user entities to UserResponse objects.", users.size());
        var userResponse = users.stream()
                .map(userToUserResponseMapper::map)
                .toList();
        log.info("Successfully mapped {} users to UserResponse objects.", userResponse.size());

        return userResponse;
    }

}
