package org.example.kihelp_back.user.repository;

import org.example.kihelp_back.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByTelegramId(Long telegramId);
    Optional<User> findByTelegramId(Long telegramId);
}
