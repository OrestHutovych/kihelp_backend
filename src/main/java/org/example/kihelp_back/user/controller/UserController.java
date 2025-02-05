package org.example.kihelp_back.user.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.dto.*;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserCreateUseCase userCreateUseCase;
    private final UserGetUseCase userGetUseCase;
    private final UserUpdateUseCase userUpdateUseCase;

    public UserController(UserCreateUseCase userCreateUseCase,
                          UserGetUseCase userGetUseCase,
                          UserUpdateUseCase userUpdateUseCase) {
        this.userCreateUseCase = userCreateUseCase;
        this.userGetUseCase = userGetUseCase;
        this.userUpdateUseCase = userUpdateUseCase;
    }

    @PostMapping("/user/auth")
    public JwtDto authUser(@RequestBody String initData) {
       return userCreateUseCase.authUser(initData);
    }

    @PutMapping("/user/{telegram_id}/toggle_role")
    public void toggleRole(@PathVariable("telegram_id") String telegramId,
                           @RequestParam(name = "role") String roleName) {
        userUpdateUseCase.toggleRole(telegramId, roleName);
    }

    @GetMapping("/")
    public List<UserDto> getUsers() {
        return userGetUseCase.getUsers();
    }

    @GetMapping("/role/{role_name}")
    public List<UserDto> getUsersByRole(@PathVariable("role_name") String roleName) {
        return userGetUseCase.findByUserRole(roleName);
    }

    @GetMapping("/user/course")
    public Integer getCourseNumber() {
        return userGetUseCase.getCourseNumberByUser();
    }

    @PatchMapping("/user/enter-course")
    public void enterCourseNumber(@RequestBody @Valid UserCourseDto userCourseDto) {
        userUpdateUseCase.enterCourseNumber(userCourseDto);
    }

    @PutMapping("/user/{telegram_id}/ban")
    public void banUser(@PathVariable("telegram_id") String telegramId,
                        @RequestParam(name = "value") boolean value) {
        userUpdateUseCase.changeBanValueByUser(telegramId, value);
    }
}
