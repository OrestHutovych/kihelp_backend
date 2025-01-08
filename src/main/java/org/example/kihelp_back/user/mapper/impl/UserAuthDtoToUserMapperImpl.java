package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserAuthDtoToUserMapper;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserAuthDtoToUserMapperImpl implements UserAuthDtoToUserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserAuthDtoToUserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User map(Map<String, Object> userRequest, Role role) {
        User user = new User();

        user.setTelegramId(String.valueOf(userRequest.get("id")));
        user.setUsername(String.valueOf( userRequest.get("username")));
        user.setPassword(passwordEncoder.encode(String.valueOf(userRequest.get("id"))));
        user.setRoles(List.of(role));

        return user;
    }
}
