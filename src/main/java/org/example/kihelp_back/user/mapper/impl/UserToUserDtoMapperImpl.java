package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserToUserDtoMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoMapperImpl implements UserToUserDtoMapper {

    @Override
    public UserDto map(User user) {
        return new UserDto(
                user.getTelegramId(),
                user.getUsername()
        );
    }
}
