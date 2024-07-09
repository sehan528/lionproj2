package org.example.lionproj2.repository;

import org.example.lionproj2.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(String name);
}
