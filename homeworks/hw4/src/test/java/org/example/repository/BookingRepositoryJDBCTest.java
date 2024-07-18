package org.example.repository;

import org.example.entity.Booking;
import org.example.entity.User;
import org.example.entity.Workplace;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@Testcontainers
class BookingRepositoryJDBCTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkplaceRepository workplaceRepository;

    @InjectMocks
    private BookingRepositoryJDBC bookingRepository;

    private static User testUserWithId;
    private static Workplace testWorkplaceWithId;
    private static Booking testBooking;

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
            statement.execute("CREATE SEQUENCE bookings_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE bookings (" +
                    "id integer PRIMARY KEY DEFAULT nextval('bookings_id_seq')," +
                    "workplace_id integer," +
                    "conference_hall_id integer," +
                    "start_time timestamp NOT NULL," +
                    "end_time timestamp NOT NULL," +
                    "user_id integer NOT NULL)");

            statement.execute("CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE users (" +
                    "id integer PRIMARY KEY DEFAULT nextval('users_id_seq')," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");

            statement.execute("CREATE SEQUENCE workplaces_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE workplaces (" +
                    "id integer PRIMARY KEY DEFAULT nextval('workplaces_id_seq')," +
                    "description VARCHAR(255) NOT NULL)");
        }

        testUserWithId = User.builder()
                .id(3)
                .username("test")
                .password("password")
                .build();

        testWorkplaceWithId = Workplace.builder()
                .id(3)
                .description("test")
                .build();

        testBooking = Booking.builder()
                .workplaceId(testWorkplaceWithId.getId())
                .hallId(null)
                .startTime(LocalDateTime.of(2024, 7, 21, 12, 0, 0))
                .endTime(LocalDateTime.of(2024, 7, 21, 13, 0, 0))
                .user(testUserWithId)
                .build();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSave() throws SQLException {
        Booking booking = bookingRepository.save(testBooking);

        assertNotNull(booking);
        assertEquals(booking.getWorkplaceId(), testBooking.getWorkplaceId());
        assertEquals(booking.getHallId(), testBooking.getHallId());
        assertEquals(booking.getStartTime(), testBooking.getStartTime());
        assertEquals(booking.getEndTime(), testBooking.getEndTime());
        assertEquals(booking.getUser().getId(), testBooking.getUser().getId());

        bookingRepository.deleteById(booking.getId());
    }

    @Test
    void testDeleteById() throws SQLException {

        int initialSize = bookingRepository.findAllBookings().size();
        Booking booking = bookingRepository.save(testBooking);
        bookingRepository.deleteById(booking.getId());
        int currentSize = bookingRepository.findAllBookings().size();

        assertEquals(initialSize, currentSize);
    }

    @Test
    void testFindById() throws SQLException {

        Booking savedBooking = bookingRepository.save(testBooking);
        Booking foundBooking = bookingRepository.findById(savedBooking.getId());

        assertNotNull(foundBooking);
        assertEquals(savedBooking.getWorkplaceId(), testBooking.getWorkplaceId());
        assertEquals(savedBooking.getHallId(), testBooking.getHallId());
        assertEquals(savedBooking.getStartTime(), testBooking.getStartTime());
        assertEquals(savedBooking.getEndTime(), testBooking.getEndTime());
        assertEquals(savedBooking.getUser().getId(), testBooking.getUser().getId());
    }

    @Test
    void testFindAllBookingsByDate() throws SQLException {

        LocalDateTime testDate = LocalDateTime.of(2024, 8, 21, 12, 12, 0);
        int initSize = bookingRepository.findAllBookingsByDate(testDate).size();

        bookingRepository.save(testBooking);
        Booking newBooking = Booking.builder()
                .workplaceId(testWorkplaceWithId.getId())
                .hallId(null)
                .startTime(LocalDateTime.of(2024, 8, 21, 12, 0, 0))
                .endTime(LocalDateTime.of(2024, 8, 21, 13, 0, 0))
                .user(testUserWithId)
                .build();
        bookingRepository.save(newBooking);
        List<Booking> bookings = bookingRepository.findAllBookingsByDate(testDate);

        assertEquals(initSize + 1, bookings.size());
    }

    @Test
    void testFindAllBookingsByResource() throws SQLException {

        int initSize = bookingRepository.findAllBookingsByResource(testWorkplaceWithId.getId()).size();
        Booking booking = bookingRepository.save(testBooking);
        List<Booking> bookings = bookingRepository.findAllBookingsByResource(testWorkplaceWithId.getId());

        assertEquals(initSize + 1, bookings.size());

        bookingRepository.deleteById(booking.getId());
    }

    @Test
    void testFindAllBookings() throws SQLException {

        int initSize = bookingRepository.findAllBookings().size();
        Booking booking = bookingRepository.save(testBooking);
        Map<Integer, Booking> bookings = bookingRepository.findAllBookings();

        assertEquals(initSize + 1, bookings.size());

        bookingRepository.deleteById(booking.getId());
    }
}