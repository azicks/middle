package ru.job4j.servlets;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public enum Role {
    ADMIN("Administrator"),
    USER("User"),
    GUEST("Guest");

    private final String role;
    private static final Map<String, Role> ENUM_MAP;

    static {
        Map<String, Role> map = new ConcurrentHashMap<>();
        for (Role instance : Role.values()) {
            map.put(instance.value(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    Role(String role) {
        this.role = role;
    }

    public static Role getRole(String name) {
        return ENUM_MAP.get(name);
    }

    public String value() {
        return role;
    }

    public static Set<String> getAll() {
        return ENUM_MAP.keySet();
    }
}
