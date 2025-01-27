package org.example.kihelp_back.user.mapper;

import org.example.kihelp_back.user.model.User;

import java.util.Map;

public interface UserMapper {
    User toEntity(Map<String, String> userDto);
}
