package com.dasun.library_manager_core.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Role findByName(String name);
}