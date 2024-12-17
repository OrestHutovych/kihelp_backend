package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByTelegramId(String telegramId);
    User findById(Long id);
    List<User> getAll();
    List<User> getByRole(String roleName);
    void changeBan(Long id, boolean value);
    void changeRole(String telegramId, String roleName);
}
