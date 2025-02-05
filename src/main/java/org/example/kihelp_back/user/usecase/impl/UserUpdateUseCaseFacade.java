package org.example.kihelp_back.user.usecase.impl;

import org.example.kihelp_back.user.dto.RoleUpdateDto;
import org.example.kihelp_back.user.dto.UserCourseDto;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.service.RoleService;
import org.example.kihelp_back.user.service.UserService;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.UserErrorMessage.ROLE_USER_NOT_CHANGE;

@Component
public class UserUpdateUseCaseFacade implements UserUpdateUseCase {
    private final UserService userService;
    private final RoleService roleService;

    public UserUpdateUseCaseFacade(UserService userService,
                                   RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void changeBanValueByUser(String telegramId, boolean value) {
        userService.changeBan(telegramId, value);
    }

    @Override
    public void toggleRole(String telegramId, RoleUpdateDto roleUpdateDto) {
        Role role = roleService.findByName(roleUpdateDto.roleName());

        if(role.getName().equals("ROLE_USER")){
            throw new IllegalArgumentException(ROLE_USER_NOT_CHANGE);
        }

        userService.changeRole(telegramId, role);
    }

    @Override
    public void enterCourseNumber(UserCourseDto userCourseDto) {
        userService.updateCourseNumber(userCourseDto.courseNumber());
    }
}
