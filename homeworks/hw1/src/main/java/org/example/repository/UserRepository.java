package org.example.repository;

import org.example.entity.User;

import java.util.Map;

public interface UserRepository {

    User save(String username, String password);

    User findByUsername(String username);

    Map<String, User> findAll();
}
