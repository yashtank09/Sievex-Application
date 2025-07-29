package com.sievex.auth.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
