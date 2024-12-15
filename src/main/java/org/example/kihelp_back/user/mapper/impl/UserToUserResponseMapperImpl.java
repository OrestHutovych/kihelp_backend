package org.example.kihelp_back.user.mapper.impl;

import org.example.kihelp_back.user.mapper.UserToUserResponseMapper;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseMapperImpl implements UserToUserResponseMapper {

    @Override
    public UserResponse map(User user) {
        return new UserResponse(
                user.getId(),
                user.getTelegramId(),
                user.getUsername()
        );
    }
}
