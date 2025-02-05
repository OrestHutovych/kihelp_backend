package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.adapters.dto.UserDto;

import java.util.List;

public interface UserGetUseCase {
    List<UserDto> getUsers();
    Integer getCourseNumberByUser();
    List<UserDto> findByUserRole(String roleName);
}
