package org.example.kihelp_back.user.service;

import org.example.kihelp_back.user.exception.RoleNotFoundException;
import org.example.kihelp_back.user.model.Role;
import org.example.kihelp_back.user.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.example.kihelp_back.user.util.UserErrorMessage.ROLE_NOT_FOUND;

@Component
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format(ROLE_NOT_FOUND, name))
                );
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public void createRolesIfNotExists(List<String> roleNames) {
        for (String roleName : roleNames) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
