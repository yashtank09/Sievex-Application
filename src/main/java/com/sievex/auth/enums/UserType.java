package com.sievex.auth.enums;

public enum UserType {
    REGULAR("regular"),
    PREMIUM("premium"),
    ADMIN("admin");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
