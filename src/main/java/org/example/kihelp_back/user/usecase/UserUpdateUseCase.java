package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserToggleBanDto;
import org.example.kihelp_back.user.dto.UserToggleRoleDto;

public interface UserUpdateUseCase {
    void changeBanValueByUser(String telegramId, UserToggleBanDto request);
    void toggleRole(String telegramId, UserToggleRoleDto request);
}
