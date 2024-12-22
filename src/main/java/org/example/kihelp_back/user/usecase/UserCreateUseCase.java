package org.example.kihelp_back.user.usecase;

import jakarta.validation.Valid;
import org.example.kihelp_back.user.dto.JwtDto;
import org.example.kihelp_back.user.dto.UserAuthDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserCreateUseCase {
    JwtDto authUser(@Valid UserAuthDto request);
}
