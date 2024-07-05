package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepositoryJDBC userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryJDBC.class);
        userService = new UserService();
        userService.repository = userRepository;
    }

    @Test
    void testAddUserSuccess() {
        User user = new User();
        when(userRepository.save(anyString(), anyString())).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Collections.emptyMap());

        User result = userService.addUser("newUser", "password");
        assertNotNull(result);
        verify(userRepository, times(1)).save(anyString(), anyString());
    }

    @Test
    void testAddUserConflict() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");
        when(userRepository.findAll()).thenReturn(Map.of("1", existingUser));

        User result = userService.addUser("existingUser", "password");
        assertNull(result);
        verify(userRepository, times(0)).save(anyString(), anyString());
    }

    @Test
    void testAddUserEmptyUsername() {
        User result = userService.addUser("", "password");
        assertNull(result);
        verify(userRepository, times(0)).save(anyString(), anyString());
    }

    @Test
    void testAuthorizeUserSuccess() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        boolean result = userService.authorizeUser(user);
        assertTrue(result);
        assertEquals(user, userService.isAuthorised());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testAuthorizeUserAlreadyLoggedIn() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        userService.authorisedUser = user;

        boolean result = userService.authorizeUser(user);
        assertFalse(result);
        verify(userRepository, times(0)).findByUsername(anyString());
    }

    @Test
    void testAuthorizeUserIncorrectCredentials() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        boolean result = userService.authorizeUser(user);
        assertFalse(result);
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testLogOutSuccess() {
        User user = new User();
        user.setUsername("user");
        userService.authorisedUser = user;

        boolean result = userService.logOut();
        assertTrue(result);
        assertNull(userService.isAuthorised());
    }

    @Test
    void testLogOutNotLoggedIn() {
        boolean result = userService.logOut();
        assertFalse(result);
    }

    @Test
    void testIsAuthorised() {
        User user = new User();
        user.setUsername("user");
        userService.authorisedUser = user;

        User result = userService.isAuthorised();
        assertEquals(user, result);
    }

    @Test
    void testGetAllUsers() {
        Map<String, User> users = Map.of("1", new User());
        when(userRepository.findAll()).thenReturn(users);

        Map<String, User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByUsernameSuccess() {
        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User result = userService.findUserByUsername("user");
        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testFindUserByUsernameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        User result = userService.findUserByUsername("unknownUser");
        assertNull(result);
        verify(userRepository, times(1)).findByUsername(anyString());
    }
}
