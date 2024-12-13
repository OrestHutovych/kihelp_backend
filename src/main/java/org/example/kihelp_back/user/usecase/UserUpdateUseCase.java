package org.example.kihelp_back.user.usecase;

import org.example.kihelp_back.user.model.UserBanRequest;

public interface UserUpdateUseCase {
    void changeBanValueByUser(Long userId, UserBanRequest request);
}
