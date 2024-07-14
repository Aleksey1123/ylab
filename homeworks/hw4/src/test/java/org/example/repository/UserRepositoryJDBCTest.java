package org.example.repository;

import org.example.entity.User;
import org.example.model.UserDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Testcontainers
class UserRepositoryJDBCTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private UserRepositoryJDBC userRepository;

    private static User testUserWithId;
    private static UserDTO testDTOUser;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("efficient_work")
            .withUsername("root")
            .withPassword("password");

    @BeforeAll
    public static void setUp() throws Exception {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE users (" +
                    "id integer PRIMARY KEY DEFAULT nextval('users_id_seq')," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");
        }

        testUserWithId = User.builder()
                .id(1)
                .username("testuser")
                .password("password")
                .build();

        testDTOUser = UserDTO.builder()
                .username("testuser")
                .password("password")
                .build();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSave() throws SQLException {
        UserDTO userDTO = testDTOUser;
        User user = userRepository.save(userDTO);

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testFindByUsername() throws SQLException {
        UserDTO userDTO = testDTOUser;
        userRepository.save(userDTO);
        User user = userRepository.findByUsername("testuser");

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testFindById() throws SQLException {

        UserDTO userDTO = testDTOUser;
        User savedUser = userRepository.save(userDTO);
        User user = userRepository.findById(savedUser.getId());

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testFindAll() throws SQLException {
        userRepository.save(UserDTO.builder().username("Test User1").password("password1").build());
        userRepository.save(UserDTO.builder().username("Test User2").password("password2").build());

        Map<String, User> users = userRepository.findAll();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }
}