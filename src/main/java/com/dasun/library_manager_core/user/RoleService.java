package com.dasun.library_manager_core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name));
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}

