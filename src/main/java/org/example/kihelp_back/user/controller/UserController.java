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

    @PostMapping("/user")
    public JwtResponse authUser(@Valid @RequestBody UserRequest user) {
       return userCreateUseCase.authUser(user);
    }

    @PutMapping("/user/{id}/toggle-role")
    public void toggleRole(@PathVariable("id") Long id, @RequestBody UserToggleRoleRequest request) {
        userUpdateUseCase.toggleRole(id, request);
    }

    @GetMapping("/user/all")
    public List<UserResponse> getUsers() {
        return userGetUseCase.getAllUsers();
    }

    @GetMapping("/user/by/role/{role_name}")
    public List<UserResponse> getUsersByRole(@PathVariable("role_name") String roleName) {
        return userGetUseCase.getUserByRole(roleName);
    }

    @PatchMapping("/user/ban/{id}")
    public void banUser(@PathVariable("id") Long userId, @RequestBody UserBanRequest request) {
        userUpdateUseCase.changeBanValueByUser(userId, request);
    }
}