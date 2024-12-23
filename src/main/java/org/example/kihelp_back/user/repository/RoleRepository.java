package org.example.kihelp_back.user.repository;

import org.example.kihelp_back.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Transactional(readOnly = true)
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
