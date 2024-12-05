package org.example.kihelp_back.task.repository;

import org.example.kihelp_back.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByTitleAndTeacherId(String title, Integer teacherId);
}
