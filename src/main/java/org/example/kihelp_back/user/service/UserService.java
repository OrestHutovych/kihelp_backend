package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.model.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByTelegramId(Long telegramId);
}
