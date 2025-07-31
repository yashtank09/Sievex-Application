package com.sievex.auth.repository;

import com.sievex.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
    boolean existsByName(String name);
    boolean existsByAlias(String alias);
}
