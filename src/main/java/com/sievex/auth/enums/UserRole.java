package com.sievex.auth.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
