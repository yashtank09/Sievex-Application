package com.sievex.auth.utils;

import com.sievex.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    private final JwtProperties jwtProperties;

    @Autowired
    public JWTUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(String userName, String role, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("email", email);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime())).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateTokenFromEmail(String email) {
        return createToken(new HashMap<>(), email);
    }

    public String extractUserName(@NotNull String token) {
        // Implement logic to extract email from the token
        return extractAllClaims(token).getSubject();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email").toString();
    }

    public String extractRole(String token) {
        // Implement logic to extract a role from the token
        return extractAllClaims(token).get("role").toString();
    }

    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractUserName(token);
        return (extractedEmail.equals(email) && isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new MalformedJwtException("Token cannot be null or empty");
        }
        try {
            return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            throw new JwtException("Invalid token: " + e.getMessage());
        }
    }

    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    public Date extractExpirationTime(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public void validateTokenFormat(String token) {
        if (token.isEmpty() || token.split("\\.").length != 3) {
            throw new MalformedJwtException("JWT structure is invalid");
        }
    }
}
