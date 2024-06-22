package org.example.service;

import org.example.entity.User;

import java.util.*;

/** Данный сервис отвечает за работу с сущностью User. Все пользователи
 * хранятся в HashMap users. В сервисе присутствует конструктор, который инициализирует
 * HashMap users и создаёт пользователя admin для тестов.
 **/
public class UserService {

    private final Map<String, User> users;

    public UserService() {

        users = new HashMap<>();
        User admin = User.builder()
                .id(UUID.randomUUID())
                .username("admin")
                .password("qwerty")
                .build();

        users.put(admin.getUsername(), admin);
    }

    public User addUser(String username, String password) {

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(String.valueOf(username))
                .password(password)
                .build();

        users.put(username, user);
        return user;
    }

    public boolean authorizeUser(String username, String password) {

        User foundUser = users.get(username);
        return foundUser != null && foundUser.getPassword().equals(password);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }
}
