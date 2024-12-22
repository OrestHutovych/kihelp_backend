package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserDto;

import java.util.List;

public interface UserGetUseCase {
    List<UserDto> getAllUsers();
    List<UserDto> getUserByRole(String roleName);
}
