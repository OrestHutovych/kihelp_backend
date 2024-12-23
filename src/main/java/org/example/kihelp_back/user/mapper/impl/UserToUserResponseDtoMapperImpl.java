package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserToUserResponseDtoMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseDtoMapperImpl implements UserToUserResponseDtoMapper {

    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getTelegramId(),
                user.getUsername()
        );
    }
}
