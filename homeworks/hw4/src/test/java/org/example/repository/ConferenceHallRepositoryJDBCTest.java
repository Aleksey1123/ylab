package org.example.repository;

import org.example.entity.ConferenceHall;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ConferenceHallRepositoryJDBCTest {

    private static ConferenceHallRepositoryJDBC hallRepository;
    private final String testDescription = "Test Description";
    private final Integer testSize = 100;

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
            statement.execute("CREATE SEQUENCE halls_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE conference_halls (" +
                    "id integer PRIMARY KEY DEFAULT nextval('halls_id_seq')," +
                    "description VARCHAR(255) NOT NULL," +
                    "size integer NOT NULL)");
        }

        hallRepository = new ConferenceHallRepositoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSave() {
        ConferenceHall hall = hallRepository.save(testDescription, testSize);

        assertNotNull(hall);
        assertEquals(testDescription, hall.getDescription());
        assertEquals(testSize, hall.getSize());
    }

    @Test
    void testUpdate() {
        ConferenceHall savedHall = hallRepository.save(testDescription, testSize);
        ConferenceHall hall = hallRepository.update(savedHall.getId(), testDescription, testSize);

        assertNotNull(hall);
        assertEquals(hall.getDescription(), testDescription);
        assertEquals(hall.getSize(), testSize);
    }

    @Test
    void testFindById() {
        ConferenceHall savedHall = hallRepository.save(testDescription, testSize);
        ConferenceHall foundHall = hallRepository.findById(savedHall.getId());

        assertNotNull(foundHall);
        assertEquals(foundHall.getId(), savedHall.getId());
        assertEquals(foundHall.getDescription(), savedHall.getDescription());
        assertEquals(foundHall.getSize(), savedHall.getSize());
    }

    @Test
    void testFindAll() {
        hallRepository.save(testDescription, testSize);
        hallRepository.save(testDescription, testSize);
        List<ConferenceHall> halls = hallRepository.findAll();

        assertNotNull(halls);
        assertTrue(halls.size() >= 2);
    }

    @Test
    void testDeleteById() {
        Integer initialSize = hallRepository.findAll().size();
        ConferenceHall savedHall = hallRepository.save(testDescription, testSize);
        ConferenceHall deletedHall = hallRepository.deleteById(savedHall.getId());
        Integer currentSize = hallRepository.findAll().size();

        assertNotNull(deletedHall);
        assertEquals(initialSize, currentSize);
    }
}