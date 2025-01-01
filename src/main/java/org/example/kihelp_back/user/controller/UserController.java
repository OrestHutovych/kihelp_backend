package org.example.kihelp_back.user.controller;

import org.example.kihelp_back.user.dto.*;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.example.kihelp_back.user.usecase.UserGetUseCase;
import org.example.kihelp_back.user.usecase.UserUpdateUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
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
    public JwtDto authUser(@RequestBody Map<String, String> query) {
       return userCreateUseCase.authUser(query);
    }

    @PutMapping("/{telegram_id}/toggle_role")
    public void toggleRole(@PathVariable("telegram_id") String telegramId, @RequestBody UserToggleRoleDto request) {
        userUpdateUseCase.toggleRole(telegramId, request);
    }

    @GetMapping("/")
    public List<UserResponseDto> getUsers() {
        return userGetUseCase.getAllUsers();
    }

    @GetMapping("/role/{role_name}")
    public List<UserResponseDto> getUsersByRole(@PathVariable("role_name") String roleName) {
        return userGetUseCase.getUserByRole(roleName);
    }

    @PatchMapping("/{telegram_id}/ban")
    public void banUser(@PathVariable("telegram_id") String telegramId, @RequestBody UserToggleBanDto request) {
        userUpdateUseCase.changeBanValueByUser(telegramId, request);
    }
}
