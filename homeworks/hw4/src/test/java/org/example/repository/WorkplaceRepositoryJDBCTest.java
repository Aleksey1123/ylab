package org.example.repository;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;
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
class WorkplaceRepositoryJDBCTest {

    @InjectMocks
    private static WorkplaceRepositoryJDBC workplaceRepository;

    private static final String testDescription = "Test Description";

    private static WorkplaceDTO workplaceDTO;

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
            statement.execute("CREATE SEQUENCE workplaces_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE workplaces (" +
                    "id integer PRIMARY KEY DEFAULT nextval('workplaces_id_seq')," +
                    "description VARCHAR(255) NOT NULL)");
        }

        workplaceDTO = WorkplaceDTO.builder()
                .description(testDescription)
                .build();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSave() throws SQLException {
        Workplace workplace = workplaceRepository.save(workplaceDTO);

        assertNotNull(workplace);
        assertEquals(testDescription, workplace.getDescription());
    }

    @Test
    void testUpdate() throws SQLException {
        Workplace savedWorkplace = workplaceRepository.save(workplaceDTO);
        Workplace workplace = workplaceRepository.update(savedWorkplace.getId(), testDescription);

        assertNotNull(workplace);
        assertEquals(workplace.getDescription(), testDescription);

        workplaceRepository.deleteById(workplace.getId());
    }

    @Test
    void testFindById() throws SQLException {
        Workplace savedWorkplace = workplaceRepository.save(workplaceDTO);
        Workplace foundWorkplace = workplaceRepository.findById(savedWorkplace.getId());

        assertNotNull(foundWorkplace);
        assertEquals(foundWorkplace.getId(), savedWorkplace.getId());
        assertEquals(foundWorkplace.getDescription(), savedWorkplace.getDescription());

        workplaceRepository.deleteById(savedWorkplace.getId());
    }

    @Test
    void testFindAll() throws SQLException {
        Workplace workplace1 = workplaceRepository.save(workplaceDTO);
        Workplace workplace2 = workplaceRepository.save(workplaceDTO);
        List<Workplace> workplaces = workplaceRepository.findAll();

        assertNotNull(workplaces);
        assertTrue(workplaces.size() >= 2);


        workplaceRepository.deleteById(workplace1.getId());
        workplaceRepository.deleteById(workplace2.getId());
    }

    @Test
    void testDeleteById() throws SQLException {
        Integer initialSize = workplaceRepository.findAll().size();
        Workplace savedWorkplace = workplaceRepository.save(workplaceDTO);
        Workplace deletedWorkplace = workplaceRepository.deleteById(savedWorkplace.getId());
        Integer currentSize = workplaceRepository.findAll().size();

        assertEquals(initialSize, currentSize);
    }
}