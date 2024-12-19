package org.example.kihelp_back.history.service;

import org.example.kihelp_back.history.model.History;

import java.util.List;

public interface HistoryService {
    void save(History history);
    List<History> getHistoryByUser(String telegramId);
}
