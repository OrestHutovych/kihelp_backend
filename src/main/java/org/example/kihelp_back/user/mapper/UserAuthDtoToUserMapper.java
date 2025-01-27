package org.example.kihelp_back.user.mapper;

import org.example.kihelp_back.global.mapper.MapperV2;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.model.User;

import java.util.Map;

public interface UserAuthDtoToUserMapper extends MapperV2<User, Map<String, String>, Role> {
}
