package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserMapper;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.service.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserMapperImpl(PasswordEncoder passwordEncoder,
                          RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User toEntity(Map<String, String> userDto) {
        User user = new User();

        user.setTelegramId(String.valueOf(userDto.get("id")));
        user.setUsername(String.valueOf(userDto.get("username")));
        user.setPassword(passwordEncoder.encode(String.valueOf(userDto.get("id"))));
        user.setRoles(List.of(findRole()));

        return user;
    }

    private Role findRole() {
        return roleService.findByName("ROLE_USER");
    }
}
