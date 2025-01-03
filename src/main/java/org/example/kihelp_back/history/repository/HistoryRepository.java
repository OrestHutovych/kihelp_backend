package org.example.kihelp_back.history.repository;

import org.example.kihelp_back.history.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Transactional(readOnly = true)
    List<History> findAllByUserTelegramId(String telegramId);
    @Transactional(readOnly = true)
    List<History> findAllByTaskId(Long taskId);
}
