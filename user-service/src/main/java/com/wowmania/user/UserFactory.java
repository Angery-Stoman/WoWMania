package com.wowmania.user;
import java.util.UUID;

public class UserFactory {
    public static User createUser(String username, String role) {
        return new User(UUID.randomUUID().toString(), username, role.toUpperCase());
    }
}