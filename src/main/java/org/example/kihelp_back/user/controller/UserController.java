package org.example.kihelp_back.user.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.model.UserBanRequest;
import org.example.kihelp_back.user.model.UserRequest;
import org.example.kihelp_back.user.model.UserResponse;
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
    public void authUser(@Valid @RequestBody UserRequest user) {
        userCreateUseCase.authUser(user);
    }

    @GetMapping("/user/all")
    public List<UserResponse> getUsers() {
        return userGetUseCase.getAllUsers();
    }

    @PatchMapping("/user/ban/{id}")
    public void banUser(@PathVariable("id") Long userId, @RequestBody UserBanRequest request) {
        userUpdateUseCase.changeBanValueByUser(userId, request);
    }
}
