package com.sievex.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SecurityUtils {

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }

    public static List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return List.of();
        }
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    public static boolean hasRole(String role) {
        if (role == null || role.isEmpty()) {
            return false;
        }
        return getCurrentUserRoles().stream()
                .anyMatch(r -> r.equals("ROLE_" + role) || r.equals(role));
    }

    public static boolean hasAnyRole(String... roles) {
        if (roles == null || roles.length == 0) {
            return false;
        }
        Collection<String> userRoles = getCurrentUserRoles();
        for (String role : roles) {
            if (userRoles.contains("ROLE_" + role) || userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
