package com.sievex.auth.service;

import com.sievex.auth.entity.UserRole;
import com.sievex.auth.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }


    @Override
    public UserRole getRoleByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public UserRole getRoleByAlias(String alias) {
        return userRoleRepository.findByAlias(alias);
    }

    @Override
    public UserRole createRole(UserRole role) {
        return userRoleRepository.save(role);
    }

    @Override
    public UserRole updateRole(UserRole role) {
        return userRoleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return userRoleRepository.existsByName(name);
    }

    @Override
    public boolean existsByAlias(String alias) {
        return userRoleRepository.existsByAlias(alias);
    }
}
