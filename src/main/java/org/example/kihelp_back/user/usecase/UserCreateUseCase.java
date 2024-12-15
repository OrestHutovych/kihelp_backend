package org.example.kihelp_back.user.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.dto.UserRequest;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserCreateUseCase {
    void authUser(@Valid UserRequest request);
}
