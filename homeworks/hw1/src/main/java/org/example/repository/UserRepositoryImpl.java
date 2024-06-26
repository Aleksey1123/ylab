package org.example.repository;

import org.example.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> users;

    public UserRepositoryImpl() {
        users = new HashMap<>();

        User admin = User.builder()
                .id(UUID.randomUUID())
                .username("admin")
                .password("qwerty")
                .build();

        users.put(admin.getUsername(), admin);
    }

    public User save(String username, String password) {

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(String.valueOf(username))
                .password(password)
                .build();

        users.put(username, user);
        return user;
    }

    public User findByUsername(String username) {

        return users.get(username);
    }

    public Map<String, User> findAll() {

        return users;
    }
}
