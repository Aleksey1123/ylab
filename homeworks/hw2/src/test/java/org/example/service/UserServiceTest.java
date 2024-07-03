//package org.example.service;
//
//import org.example.entity.User;
//import org.example.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Map;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @Mock
//    private UserRepository repository;
//    @InjectMocks
//    private UserService service;
//
//    private final UUID id = UUID.randomUUID();
//    private final String testUsername = "test";
//    private final String testPassword = "test";
//
//    private final User testUser = User.builder()
//            .id(id)
//            .username(testUsername)
//            .password(testPassword)
//            .build();
//
//    @Test
//    void testAddUser() {
//
//        when(repository.save(any(), any())).thenReturn(testUser);
//        User user = service.addUser(testUsername, testPassword);
//        assertThat(user).isEqualTo(testUser);
//    }
//
//    @Test
//    void testAuthorizeUserWithSuccess() {
//
//        when(repository.findByUsername(testUsername)).thenReturn(testUser);
//        assertTrue(service.authorizeUser(testUsername, testPassword));
//    }
//
//    @Test
//    void testAuthorizeUserWithFailure() {
//
//        when(repository.findByUsername(testUsername)).thenReturn(null);
//        assertFalse(service.authorizeUser(testUsername, testPassword));
//    }
//
//    @Test
//    void testGetUserByUsername() {
//
//        when(repository.findByUsername(any())).thenReturn(testUser);
//        User user = service.getUserByUsername(testUsername);
//        assertThat(user).isEqualTo(testUser);
//    }
//
//    @Test
//    void testGetAllUsers() {
//
//        when(repository.findAll()).thenReturn(Map.of(testUsername, testUser));
//        Map<String, User> users = service.getAllUsers();
//        assertThat(users.size()).isEqualTo(1);
//        assertThat(users.get(testUsername)).isEqualTo(testUser);
//    }
//}