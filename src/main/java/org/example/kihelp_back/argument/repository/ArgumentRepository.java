package org.example.kihelp_back.argument.repository;

import org.example.kihelp_back.argument.model.Argument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArgumentRepository extends JpaRepository<Argument, Integer> {
    boolean existsByName(String name);
    Optional<Argument> findByName(String name);
}
