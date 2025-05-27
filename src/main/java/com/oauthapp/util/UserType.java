package com.oauthapp.util;

public enum UserType {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
