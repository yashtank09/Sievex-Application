package com.sievex.auth.service;

import com.sievex.auth.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getAllRoles();
    UserRole getRoleByName(String name);
    UserRole getRoleByAlias(String alias);
    UserRole createRole(UserRole role);
    UserRole updateRole(UserRole role);
    void deleteRole(Long id);
    boolean existsByName(String name);
    boolean existsByAlias(String alias);
}
