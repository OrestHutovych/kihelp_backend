package org.example.kihelp_back.argument.service;

import org.example.kihelp_back.argument.model.Argument;

public interface ArgumentService {
    void create(Argument argument);
    Argument getById(Integer id);
}
