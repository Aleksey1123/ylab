package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepositoryJDBC;

import java.util.Map;

/** Данный сервис отвечает за работу с сущностью User. Все пользователи
 * хранятся в HashMap users. В сервисе присутствует конструктор, который инициализирует
 * HashMap users и создаёт пользователя admin для тестов.
 **/
public class UserService {

    private UserRepositoryJDBC repository;

    public UserService() {
        repository = new UserRepositoryJDBC();
    }

    public User addUser(String username, String password) {

        return repository.save(username, password);
    }

    public boolean authorizeUser(String username, String password) {

        User foundUser = repository.findByUsername(username);
        return foundUser != null && foundUser.getPassword().equals(password);
    }

    public User getUserByUsername(String username) {

        return repository.findByUsername(username);
    }

    public Map<String, User> getAllUsers() {

        return repository.findAll();
    }
}
