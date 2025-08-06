package com.sievex.auth.service;

public interface TokenService {
    /**
     * Adds a token to the blacklist with TTL matching token's expiration
     * @param token The JWT token to blacklist
     * @param expirationTime Token expiration time in milliseconds since epoch
     */
    void blacklistToken(String token, long expirationTime);
    
    /**
     * Checks if a token is in the blacklist
     * @param token The JWT token to check
     * @return true if token is blacklisted, false otherwise
     */
    boolean isTokenBlacklisted(String token);
}