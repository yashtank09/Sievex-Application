package com.sievex.auth.service.impl;

import com.sievex.auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Autowired
    public TokenServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void blacklistToken(String token, long expirationTime) {
        long currentTime = System.currentTimeMillis() / 1000;
        long ttl = expirationTime - currentTime;
        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + token,
                    "blacklisted",
                    ttl,
                    TimeUnit.SECONDS
            );
        }

    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        Object isBlacklisted = redisTemplate.opsForValue().get(BLACKLIST_PREFIX + token);
        if (isBlacklisted == null) {
            return false;
        }
        return isBlacklisted.equals("blacklisted");
    }
}
