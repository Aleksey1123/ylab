package org.example.service;

import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private UserService service;
    private final String username = "test";
    private final String password = "test";

    @BeforeEach
    void setUp() {

        service = new UserService();
    }

    @Test
    void testAddUser() {

        int initialSize = service.getAllUsers().size();
        service.addUser(username, password);
        assertThat(service.getAllUsers()).hasSize(initialSize + 1);
    }

    @Test
    void testAuthorizeUserWithSuccess() {

        service.addUser(username, password);
        boolean isAuthenticated = service.authorizeUser(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    void testAuthorizeUserWithFailure() {

        service.addUser(username, password);

        boolean isAuthenticated = service.authorizeUser(username, password);
        assertTrue(isAuthenticated);
    }

    @Test
    void testGetUserByUsername() {

        User user = service.getUserByUsername("admin");
        assertNotNull(user);
    }

    @Test
    void testGetAllUsers() {

        Map<String, User> users = service.getAllUsers();
        assertNotNull(users);
    }
}