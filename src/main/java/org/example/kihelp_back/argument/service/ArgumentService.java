package org.example.kihelp_back.argument.service;

import org.example.kihelp_back.argument.model.Argument;
import org.example.kihelp_back.argument.dto.ArgumentUpdateRequest;

import java.util.List;

public interface ArgumentService {
    void create(Argument argument);
    Argument getById(Integer id);
    List<Argument> getAll();
    void delete(Integer id);
    void update(Integer id, ArgumentUpdateRequest request);
}
