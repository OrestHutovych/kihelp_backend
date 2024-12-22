package org.example.kihelp_back.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.repository.RoleRepository;
import org.springframework.stereotype.Component;

import static org.example.kihelp_back.user.util.ErrorMessage.ROLE_NOT_FOUND;

@Component
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        log.info("Attempting to find role with name {}", name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format(ROLE_NOT_FOUND, name))
                );
    }

    public boolean existsByName(String name) {
        log.info("Checking if role with name {} exists", name);
        return roleRepository.existsByName(name);
    }
}
