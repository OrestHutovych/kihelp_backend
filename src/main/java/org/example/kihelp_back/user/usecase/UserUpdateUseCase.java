package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserBanRequest;
import org.example.kihelp_back.user.dto.UserToggleRoleRequest;

public interface UserUpdateUseCase {
    void changeBanValueByUser(String telegramId, UserBanRequest request);
    void toggleRole(String telegramId, UserToggleRoleRequest request);
}
