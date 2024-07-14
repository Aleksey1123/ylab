package org.example.service;

import org.example.entity.User;
import org.example.model.UserDTO;
import org.example.repository.UserRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryJDBC userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO testUser = UserDTO.builder()
            .username("user")
            .password("Build")
            .build();

    @Test
    void testAddUserSuccess() throws SQLException {
        when(userRepository.save(any(UserDTO.class))).thenReturn(new User());

        User result = userService.addUser(testUser);
        assertNotNull(result);
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
    void testGetAllUsers() throws SQLException {
        Map<String, User> users = Map.of("1", new User());
        when(userRepository.findAll()).thenReturn(users);

        Map<String, User> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByUsernameSuccess() throws SQLException {
        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        User result = userService.findUserByUsername("user");
        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername(anyString());
    }

}
