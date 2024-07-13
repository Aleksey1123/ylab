package org.example.repository;

import org.example.entity.Workplace;
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

//@Testcontainers
//class WorkplaceRepositoryJDBCTest {
//
//    private static WorkplaceRepositoryJDBC workplaceRepository;
//    private final String testDescription = "Test Description";
//
//    @Container
//    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("efficient_work")
//            .withUsername("root")
//            .withPassword("password");
//
//    @BeforeAll
//    public static void setUp() throws Exception {
//        postgreSQLContainer.start();
//        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
//        String username = postgreSQLContainer.getUsername();
//        String password = postgreSQLContainer.getPassword();
//
//        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//             Statement statement = connection.createStatement()) {
//            statement.execute("CREATE SEQUENCE workplaces_id_seq START WITH 1" +
//                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");
//
//            statement.execute("CREATE TABLE workplaces (" +
//                    "id integer PRIMARY KEY DEFAULT nextval('workplaces_id_seq')," +
//                    "description VARCHAR(255) NOT NULL)");
//        }
//
//        workplaceRepository = new WorkplaceRepositoryJDBC() {
//            @Override
//            public Connection getConnection() throws SQLException {
//                return DriverManager.getConnection(jdbcUrl, username, password);
//            }
//        };
//    }
//
//    @AfterAll
//    static void tearDown() {
//        postgreSQLContainer.stop();
//    }
//
//    @Test
//    void testSave() {
//        Workplace workplace = workplaceRepository.save(testDescription);
//
//        assertNotNull(workplace);
//        assertEquals(testDescription, workplace.getDescription());
//    }
//
//    @Test
//    void testUpdate() {
//        Workplace savedWorkplace = workplaceRepository.save(testDescription);
//        Workplace workplace = workplaceRepository.update(savedWorkplace.getId(), testDescription);
//
//        assertNotNull(workplace);
//        assertEquals(workplace.getDescription(), testDescription);
//    }
//
//    @Test
//    void testFindById() {
//        Workplace savedWorkplace = workplaceRepository.save(testDescription);
//        Workplace foundWorkplace = workplaceRepository.findById(savedWorkplace.getId());
//
//        assertNotNull(foundWorkplace);
//        assertEquals(foundWorkplace.getId(), savedWorkplace.getId());
//        assertEquals(foundWorkplace.getDescription(), savedWorkplace.getDescription());
//    }
//
//    @Test
//    void testFindAll() {
//        workplaceRepository.save(testDescription);
//        workplaceRepository.save(testDescription);
//        List<Workplace> workplaces = workplaceRepository.findAll();
//
//        assertNotNull(workplaces);
//        assertTrue(workplaces.size() >= 2);
//    }
//
//    @Test
//    void testDeleteById() {
//        Integer initialSize = workplaceRepository.findAll().size();
//        Workplace savedWorkplace = workplaceRepository.save(testDescription);
//        Workplace deletedWorkplace = workplaceRepository.deleteById(savedWorkplace.getId());
//        Integer currentSize = workplaceRepository.findAll().size();
//
//        assertNotNull(deletedWorkplace);
//        assertEquals(initialSize, currentSize);
//    }
//}