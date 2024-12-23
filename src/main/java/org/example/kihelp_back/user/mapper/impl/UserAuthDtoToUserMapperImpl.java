package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserAuthDtoToUserMapper;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.dto.UserAuthDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAuthDtoToUserMapperImpl implements UserAuthDtoToUserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserAuthDtoToUserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User map(UserAuthDto userRequest, Role role) {
        User user = new User();

        user.setTelegramId(userRequest.telegramId());
        user.setUsername(userRequest.username());
        user.setPassword(passwordEncoder.encode(userRequest.telegramId()));
        user.setRoles(List.of(role));

        return user;
    }
}
