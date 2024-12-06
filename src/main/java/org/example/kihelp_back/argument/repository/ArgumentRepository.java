package org.example.kihelp_back.argument.repository;

import org.example.kihelp_back.argument.model.Argument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArgumentRepository extends JpaRepository<Argument, Integer> {
    boolean existsByName(String name);
}
