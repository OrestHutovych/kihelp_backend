package org.example.kihelp_back.subject.repository;

import org.example.kihelp_back.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByTitleAndTeacherId(String title, Integer teacherId);
    List<Task> findByTeacherId(Integer teacherId);
}
