package org.example.kihelp_back.user.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.adapters.dto.JwtDto;
import org.example.kihelp_back.user.adapters.dto.RoleUpdateDto;
import org.example.kihelp_back.user.adapters.dto.UserCourseDto;
import org.example.kihelp_back.user.adapters.dto.UserDto;
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

    @PostMapping("/auth")
    public JwtDto authUser(@RequestBody String initData) {
       return userCreateUseCase.authUser(initData);
    }

    @PatchMapping("/{telegramId}/roles")
    public void toggleRole(
            @PathVariable("telegramId") String telegramId,
            @RequestBody RoleUpdateDto roleUpdateDto) {
        userUpdateUseCase.toggleRole(telegramId, roleUpdateDto);
    }

    @GetMapping("/")
    public List<UserDto> getUsers() {
        return userGetUseCase.getUsers();
    }

    @GetMapping("/role/{roleName}")
    public List<UserDto> getUsersByRole(@PathVariable("roleName") String roleName) {
        return userGetUseCase.findByUserRole(roleName);
    }

    @GetMapping("/me/course")
    public Integer getCourseNumber() {
        return userGetUseCase.getCourseNumberByUser();
    }

    @PatchMapping("/me/course")
    public void enterCourseNumber(@RequestBody @Valid UserCourseDto userCourseDto) {
        userUpdateUseCase.enterCourseNumber(userCourseDto);
    }

    @PutMapping("/{telegramId}/ban")
    public void banUser(@PathVariable("telegramId") String telegramId,
                        @RequestParam(name = "value") boolean value) {
        userUpdateUseCase.changeBanValueByUser(telegramId, value);
    }
}
