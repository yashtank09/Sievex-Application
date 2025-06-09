package com.unifiedapp.auth.utils;

import com.unifiedapp.configs.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

    public String generateToken(String userName, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime())).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateTokenFromEmail(String email) {
        return createToken(new HashMap<>(), email);
    }

    public String extractUserName(String token) {
        // Implement logic to extract email from the token
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        // Implement logic to extract a role from the token
        return extractAllClaims(token).get("role").toString();
    }

    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractUserName(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token).getBody();
    }

    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }
}
