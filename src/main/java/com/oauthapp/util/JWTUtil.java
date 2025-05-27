package com.oauthapp.util;

import com.oauthapp.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    private JwtProperties jwtProperties;

    @Autowired
    public JWTUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime())).signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()).compact();
    }

    public String generateTokenFromEmail(String email) {
        return createToken(new HashMap<>(), email);
    }

    public String extractEmail(String token) {
        // Implement logic to extract email from the token
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        // Implement logic to extract a role from the token
        return extractAllClaims(token).get("role").toString();
    }

    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
