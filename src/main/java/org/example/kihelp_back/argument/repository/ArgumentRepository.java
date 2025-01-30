package org.example.kihelp_back.argument.repository;

import org.example.kihelp_back.argument.model.Argument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ArgumentRepository extends JpaRepository<Argument, Long> {
    boolean existsByName(String name);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tasks_arguments WHERE argument_id = :argumentId", nativeQuery = true)
    void deleteTaskArgumentsByArgumentId(Long argumentId);
}
