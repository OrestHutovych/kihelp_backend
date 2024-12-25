package org.example.kihelp_back.task.repository;

import org.example.kihelp_back.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitleAndTeacherId(String title, Long teacherId);
    @Transactional(readOnly = true)
    List<Task> findByTeacherId(Long teacherId);
}
