package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.JwtDto;
import org.example.kihelp_back.user.model.User;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
public interface UserCreateUseCase {
    JwtDto authUser(String initData);
    User create(Map<String, String> query);
}
