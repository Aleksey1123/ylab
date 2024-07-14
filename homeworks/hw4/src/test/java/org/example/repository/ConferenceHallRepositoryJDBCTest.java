package org.example.repository;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Testcontainers
class ConferenceHallRepositoryJDBCTest {

    @InjectMocks
    private static ConferenceHallRepositoryJDBC hallRepository;

    private static final String testDescription = "Test Description";
    private static final Integer testSize = 100;
    
    private static ConferenceHallDTO testDTOHall;

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

        createTables(jdbcUrl, username, password);
        
        testDTOHall = ConferenceHallDTO.builder()
                .description(testDescription)
                .size(testSize.toString())
                .build();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    private static void createTables(String jdbcUrl, String username, String password) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE SEQUENCE halls_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE conference_halls (" +
                    "id integer PRIMARY KEY DEFAULT nextval('halls_id_seq')," +
                    "description VARCHAR(255) NOT NULL," +
                    "size integer NOT NULL)");
        }
    }

    @Test
    void testSave() throws SQLException {
        ConferenceHall hall = hallRepository.save(testDTOHall);

        assertNotNull(hall);
        assertEquals(testDescription, hall.getDescription());
        assertEquals(testSize, hall.getSize());

        hallRepository.deleteById(hall.getId());
    }

    @Test
    void testUpdate() throws SQLException {
        ConferenceHall savedHall = hallRepository.save(testDTOHall);
        ConferenceHall hall = hallRepository.update(savedHall.getId(), testDescription, testSize);

        assertNotNull(hall);
        assertEquals(testDescription, hall.getDescription());
        assertEquals(testSize, hall.getSize());

        hallRepository.deleteById(savedHall.getId());
    }

    @Test
    void testFindById() throws SQLException {
        ConferenceHall savedHall = hallRepository.save(testDTOHall);
        ConferenceHall foundHall = hallRepository.findById(savedHall.getId());

        assertNotNull(foundHall);
        assertEquals(savedHall.getId(), foundHall.getId());
        assertEquals(savedHall.getDescription(), foundHall.getDescription());
        assertEquals(savedHall.getSize(), foundHall.getSize());

        hallRepository.deleteById(savedHall.getId());
    }

    @Test
    void testFindAll() throws SQLException {

        int initSize = hallRepository.findAll().size();
        ConferenceHall hall1 = hallRepository.save(testDTOHall);
        ConferenceHall hall2 = hallRepository.save(testDTOHall);
        List<ConferenceHall> halls = hallRepository.findAll();

        assertNotNull(halls);
        assertEquals(initSize + 2, halls.size());

        hallRepository.deleteById(hall1.getId());
        hallRepository.deleteById(hall2.getId());
    }

    @Test
    void testDeleteById() throws SQLException {
        Integer initialSize = hallRepository.findAll().size();
        ConferenceHall savedHall = hallRepository.save(testDTOHall);
        ConferenceHall deletedHall = hallRepository.deleteById(savedHall.getId());
        Integer currentSize = hallRepository.findAll().size();

        assertEquals(initialSize, currentSize);
    }
}