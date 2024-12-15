package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserResponse;

import java.util.List;

public interface UserGetUseCase {
    List<UserResponse> getAllUsers();
}
