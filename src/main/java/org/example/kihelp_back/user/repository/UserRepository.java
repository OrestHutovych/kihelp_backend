package org.example.kihelp_back.user.repository;

import org.example.kihelp_back.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByTelegramId(String telegramId);
    @Transactional(readOnly = true)
    @Modifying
    @Query(value = "SELECT u.* FROM users u " +
            "JOIN users_roles ur ON u.id = ur.user_id " +
            "JOIN roles r ON ur.role_id = r.id " +
            "WHERE r.name = :roleName",
            nativeQuery = true)
    List<User> findByRoleName(String roleName);
    boolean existsByTelegramId(String telegramId);
}
