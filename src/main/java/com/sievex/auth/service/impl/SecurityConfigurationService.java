package com.sievex.auth.service.impl;

import com.sievex.auth.entity.SecurityConfigurations;
import com.sievex.auth.enums.AccessLevel;
import com.sievex.auth.repository.SecurityConfigurationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfigurationService.class);

    private final SecurityConfigurationRepository securityConfigurationRepository;
    private List<SecurityConfigurations> cachedConfigs;


    @PostConstruct
    @Scheduled(fixedRate = 60000)
    public void loadConfigs() {
        cachedConfigs = securityConfigurationRepository.findAll();
    }

    /**
     * Get paths for moderator access level
     * @return List of moderator paths
     */
    public List<String> getModeratorPaths() {
        return getPathsByAccessLevel(AccessLevel.MODERATOR);
    }

    /**
     * Get paths for super admin access level
     * @return List of super admin paths
     */
    public List<String> getSuperAdminPaths() {
        return getPathsByAccessLevel(AccessLevel.SUPER_ADMIN);
    }

    /**
     * Get all active security configurations
     * @return List of all active configurations
     */
    public List<SecurityConfigurations> getAllActiveConfigs() {
        if (cachedConfigs == null) {
            loadConfigs();
        }
        return cachedConfigs;
    }

    /**
     * Get paths by specific access level
     * @param accessLevel The access level to filter by
     * @return List of paths for the specified access level
     */
    private List<String> getPathsByAccessLevel(AccessLevel accessLevel) {
        if (cachedConfigs == null) {
            loadConfigs();
        }

        return cachedConfigs.stream()
                .filter(config -> config.getAccessLevel() == accessLevel)
                .map(SecurityConfigurations::getPath)
                .collect(Collectors.toList());
    }

    /**
     * Force reload configurations from database
     */
    public void forceReload() {
        logger.info("Force reloading security configurations");
        loadConfigs();
    }
}
