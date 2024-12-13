package org.example.kihelp_back.user.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.mapper.UserRequestToUserMapper;
import org.example.kihelp_back.user.model.UserRequest;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCreateUseCaseImpl implements UserCreateUseCase {
    private final UserService userService;
    private final UserRequestToUserMapper userRequestToUserMapper;
    private final RoleService roleService;

    public UserCreateUseCaseImpl(UserService userService,
                                 UserRequestToUserMapper userRequestToUserMapper,
                                 RoleService roleService) {
        this.userService = userService;
        this.userRequestToUserMapper = userRequestToUserMapper;
        this.roleService = roleService;
    }

    @Override
    public void authUser(UserRequest request) {
        var role = roleService.findByName("ROLE_USER");

        log.debug("Mapping UserRequest {} entity to User objects.", request);
        var user = userRequestToUserMapper.map(request, role);
        log.info("Successfully mapped {} UserRequest to User objects.", user);

        userService.save(user);
    }
}
