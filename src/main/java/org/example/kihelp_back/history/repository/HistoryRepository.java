package org.example.kihelp_back.history.repository;

import org.example.kihelp_back.history.model.History;
import org.example.kihelp_back.history.model.HistoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Transactional(readOnly = true)
    List<History> findAllByUserTelegramId(String telegramId);
    @Transactional(readOnly = true)
    List<History> findAllByTaskId(Long taskId);
    @Transactional(readOnly = true)
    @Query("""
        SELECT h FROM History h
        JOIN h.task t
        JOIN t.developer d
        WHERE d.telegramId = :developerTelegramId
        AND h.status = :status
    """)
    List<History> findTaskInProgressByDeveloperTelegramId(@Param("developerTelegramId") String developerTelegramId,
                                                          @Param("status") HistoryStatus status);
}
