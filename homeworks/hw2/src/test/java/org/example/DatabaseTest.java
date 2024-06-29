package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

    private static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("testDB")
                .withUsername("testUser")
                .withPassword("testPassword");
        postgreSQLContainer.start();
    }

    @Test
    public void  testDataBaseConnection() throws SQLException {
        String url = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection successful");
        }
    }
}
