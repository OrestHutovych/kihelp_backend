package org.example.kihelp_back.user.service.impl;

import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.repository.RoleRepository;
import org.example.kihelp_back.user.service.RoleService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.ROLE_NOT_FOUND;

@Component
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format(ROLE_NOT_FOUND, name))
                );
    }
}
