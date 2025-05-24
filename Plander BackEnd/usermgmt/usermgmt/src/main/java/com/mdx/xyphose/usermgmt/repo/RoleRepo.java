package com.mdx.xyphose.usermgmt.repo;

import com.mdx.xyphose.usermgmt.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}