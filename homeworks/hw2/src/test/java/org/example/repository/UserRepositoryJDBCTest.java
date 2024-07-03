package org.example.repository;

import org.example.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserRepositoryJDBCTest {

    private static UserRepositoryJDBC userRepository;
    private final String testUsername = "Test User";
    private final String testPassword = "password";

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
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
            statement.execute("CREATE SEQUENCE users_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE users (" +
                    "id integer PRIMARY KEY DEFAULT nextval('users_id_seq')," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");
        }

        userRepository = new UserRepositoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testSave() {
        User user = userRepository.save(testUsername, testPassword);
        
        assertNotNull(user);
        assertEquals(testUsername, user.getUsername());
        assertEquals(testPassword, user.getPassword());
    }

    @Test
    public void testFindByUsername() {
        userRepository.save(testUsername, testPassword);
        User user = userRepository.findByUsername(testUsername);

        assertNotNull(user);
        assertEquals(testUsername, user.getUsername());
        assertEquals(testPassword, user.getPassword());
    }

    @Test
    public void testFindById() {
        User savedUser = userRepository.save(testUsername, testPassword);
        User user = userRepository.findById(savedUser.getId());

        assertNotNull(user);
        assertEquals(testUsername, user.getUsername());
        assertEquals(testPassword, user.getPassword());
    }

    @Test
    public void testFindAll() {
        userRepository.save("Test User1", "password1");
        userRepository.save("Test User2", "password2");

        Map<String, User> users = userRepository.findAll();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }
}