package org.example.kihelp_back.subject.repository;

import org.example.kihelp_back.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByTitleAndTeacherId(String title, Integer teacherId);
    List<Task> findByTeacherId(Integer teacherId);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tasks_arguments WHERE task_id = :taskId", nativeQuery = true)
    void deleteAllArgumentByTaskId(Integer taskId);
}
