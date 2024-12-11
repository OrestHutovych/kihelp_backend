package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.model.Role;

public interface RoleService {
    Role findByName(String name);
}
