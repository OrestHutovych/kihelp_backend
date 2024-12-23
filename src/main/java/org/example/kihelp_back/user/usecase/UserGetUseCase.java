package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserResponseDto;

import java.util.List;

public interface UserGetUseCase {
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getUserByRole(String roleName);
}
