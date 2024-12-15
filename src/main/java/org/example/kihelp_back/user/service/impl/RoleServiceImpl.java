package org.example.kihelp_back.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.repository.RoleRepository;
import org.example.kihelp_back.user.service.RoleService;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.ROLE_NOT_FOUND;

@Component
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        log.info("Attempting to find role with name {}", name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Role with name {} not found. Throwing RoleNotFoundException.", name);
                    return new RoleNotFoundException(
                            String.format(ROLE_NOT_FOUND, name));
                });
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
