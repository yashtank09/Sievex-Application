package com.sievex.auth.repository;

import com.sievex.auth.entity.Users;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Users findByUserName(String username);
    Users findByUserNameOrEmail(String username, String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUserNameOrEmail(String username, String email);
    @Modifying
    @Query("UPDATE Users u SET u.password = :password WHERE u.userName = :userName")
    int updatePassword(@Param("userName") String userName, @Param("password") String password);
}
