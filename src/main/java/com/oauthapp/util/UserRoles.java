package com.oauthapp.util;

public enum UserRoles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR");

    private final String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
