package com.sievex.auth.repository;

import com.sievex.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Users findByUserName(String username);
    Users findByUserNameOrEmail(String username, String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUserNameOrEmail(String username, String email);
}
