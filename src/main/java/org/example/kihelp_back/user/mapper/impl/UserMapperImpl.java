package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.adapters.dto.UserDto;
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
        if(userDto == null) {
            return null;
        }

        User user = new User();

        user.setTelegramId(userDto.get("id"));
        user.setUsername(userDto.get("username"));
        user.setPassword(encodePassword(userDto.get("id")));
        user.setRoles(List.of(findRole()));

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        if(user == null) {
            return null;
        }

        return new UserDto(
                user.getTelegramId(),
                user.getUsername(),
                user.getCourseNumber(),
                user.getCreatedAt().toString()
        );
    }

    private Role findRole() {
        return roleService.findByName("ROLE_USER");
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
