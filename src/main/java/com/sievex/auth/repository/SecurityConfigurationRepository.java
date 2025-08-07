package com.sievex.auth.repository;

import com.sievex.auth.entity.SecurityConfigurations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityConfigurationRepository extends JpaRepository<SecurityConfigurations, Long> {
    List<SecurityConfigurations> findAllByIsActive(Boolean isActive);
}
