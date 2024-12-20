package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findByTelegramId(String telegramId);
    User findById(Long id);
    User findByJwt();
    List<User> getAll();
    List<User> getByRole(String roleName);
    void changeBan(String telegramId, boolean value);
    void changeRole(String telegramId, String roleName);
}
