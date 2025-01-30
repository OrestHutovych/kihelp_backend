package org.example.kihelp_back.user.usecase;

public interface UserUpdateUseCase {
    void changeBanValueByUser(String telegramId, boolean value);
    void toggleRole(String telegramId, String roleName);
}
