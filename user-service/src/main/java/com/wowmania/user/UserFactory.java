package com.wowmania.user;
import java.util.UUID;

public class UserFactory {
    public static User createUser(String username, String role) {
        String defaultPassword = "_password_";
        String defaultEmail = username.toLowerCase() + "@wowmania.com";

        return new User(
                UUID.randomUUID().toString(),
                username,
                defaultPassword,
                role.toUpperCase(),
                defaultEmail
        );
    }
}