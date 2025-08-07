package com.sievex.auth.enums;

/**
 * Enum representing different access levels for security configurations
 * Used to define who can access specific endpoints in the application
 */
public enum AccessLevel {
    /**
     * Public access - no authentication required
     */
    PUBLIC,

    /**
     * User access - requires authentication as a regular user
     */
    USER,

    /**
     * Admin access - requires admin privileges
     */
    ADMIN,

    /**
     * Moderator access - requires moderator privileges
     */
    MODERATOR,

    /**
     * Super admin access - requires super admin privileges
     */
    SUPER_ADMIN
}