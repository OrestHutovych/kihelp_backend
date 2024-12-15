package org.example.kihelp_back.user.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;
import org.example.kihelp_back.user.dto.UserRequest;

public interface UserRequestToUserMapper extends MapperV2<User, UserRequest, Role> {
}
