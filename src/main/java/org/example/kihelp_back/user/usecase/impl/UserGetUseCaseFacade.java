package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.mapper.UserToUserResponseDtoMapper;
import org.example.kihelp_back.user.dto.UserResponseDto;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserGetUseCaseFacade implements UserGetUseCase {
    private final UserService userService;
    private final UserToUserResponseDtoMapper userToUserResponseDtoMapper;

    public UserGetUseCaseFacade(UserService userService,
                                UserToUserResponseDtoMapper userToUserResponseDtoMapper) {
        this.userService = userService;
        this.userToUserResponseDtoMapper = userToUserResponseDtoMapper;
    }

    @Override
    public List<UserResponseDto> getUserByRole(String roleName) {
        List<User> users = userService.getByRole(roleName);

        log.info("Mapping User(s) {} to UserResponseDto(s)", users.size());
        List<UserResponseDto> responseUser =  users.stream()
                .map(userToUserResponseDtoMapper::map)
                .toList();

        log.info("Successfully mapped UserResponseDto()s {} and return.", responseUser.size());
        return responseUser;
    }
}
