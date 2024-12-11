package org.example.kihelp_back.user.controller;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.model.UserRequest;
import org.example.kihelp_back.user.usecase.UserCreateUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserCreateUseCase userCreateUseCase;

    public UserController(UserCreateUseCase userCreateUseCase) {
        this.userCreateUseCase = userCreateUseCase;
    }

    @PostMapping("/user")
    public void authUser(@Valid @RequestBody UserRequest user) {
        userCreateUseCase.authUser(user);
    }
}
