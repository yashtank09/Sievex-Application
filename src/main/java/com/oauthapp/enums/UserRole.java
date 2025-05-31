package com.oauthapp.enums;

public enum UserRole {
    USER("user"),
    MODERATOR("moderator"),
    ADMIN("admin"),
    SUPER_ADMIN("super_admin");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
