package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.JwtDto;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserCreateUseCase {
    JwtDto authUser(String initData);
}
