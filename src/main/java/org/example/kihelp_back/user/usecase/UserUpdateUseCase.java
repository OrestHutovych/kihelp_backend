package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.dto.UserBanRequest;

public interface UserUpdateUseCase {
    void changeBanValueByUser(Long userId, UserBanRequest request);
}
