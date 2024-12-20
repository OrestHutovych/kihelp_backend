package org.example.kihelp_back.history.repository;

import org.example.kihelp_back.history.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllByUserTelegramId(String telegramId);
    List<History> findAllByTaskId(Integer taskId);
}
