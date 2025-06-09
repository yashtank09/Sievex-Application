package com.unifiedapp.auth.utils;

public class UserUtils {
    public static String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0];
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        return baseUsername + "_" + uniqueSuffix;
    }
}
