package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.JwtDto;

public interface UserCreateUseCase {
    JwtDto authUser(String initData);
}
