package com.wargame.domain;

public enum UserRole {
    USER("User"),
    ADMIN("Admin");
    private String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
