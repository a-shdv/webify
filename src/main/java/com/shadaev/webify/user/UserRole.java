package com.shadaev.webify.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name(); // строковое представление USER, ADMIN
    }
}
