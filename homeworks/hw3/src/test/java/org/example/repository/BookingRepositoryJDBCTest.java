package org.example.repository;

import org.example.entity.Booking;
import org.example.entity.User;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
class BookingRepositoryJDBCTest {

    private static BookingRepositoryJDBC bookingRepository;
    private static UserRepositoryJDBC userRepository;
    private static WorkplaceRepositoryJDBC workplaceRepository;

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
            statement.execute("CREATE SEQUENCE bookings_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE bookings (" +
                    "id integer PRIMARY KEY DEFAULT nextval('bookings_id_seq')," +
                    "workplace_id integer," +
                    "conference_hall_id integer," +
                    "start_time timestamp NOT NULL," +
                    "end_time timestamp NOT NULL," +
                    "user_id integer NOT NULL)");

            statement.execute("CREATE SEQUENCE users_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE users (" +
                    "id integer PRIMARY KEY DEFAULT nextval('users_id_seq')," +
                    "username VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL)");

            statement.execute("CREATE SEQUENCE workplaces_id_seq START WITH 1" +
                    " INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1");

            statement.execute("CREATE TABLE workplaces (" +
                    "id integer PRIMARY KEY DEFAULT nextval('workplaces_id_seq')," +
                    "description VARCHAR(255) NOT NULL)");
        }

        bookingRepository = new BookingRepositoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };
        userRepository = new UserRepositoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };
        workplaceRepository = new WorkplaceRepositoryJDBC() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };

        User user = User.builder()
                .username("test")
                .password("password")
                .build();
        testUserWithId = userRepository.save(user.getUsername(), user.getPassword());

        Workplace workplace = Workplace.builder()
                .description("test")
                .build();
        testWorkplaceWithId = workplaceRepository.save(workplace.getDescription());

        testBooking = Booking.builder()
                .workplaceId(1)
                .hallId(null)
                .startTime(LocalDateTime.of(2024,7,21,12,0,0))
                .endTime(LocalDateTime.of(2024,7,21,13,0,0))
                .user(testUserWithId)
                .build();

//        testBooking = bookingRepository.save(booking);
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSave() {
        Booking booking = bookingRepository.save(testBooking);

        assertNotNull(booking);
        assertEquals(booking.getWorkplaceId(), testBooking.getWorkplaceId());
        assertEquals(booking.getHallId(), testBooking.getHallId());
        assertEquals(booking.getStartTime(), testBooking.getStartTime());
        assertEquals(booking.getEndTime(), testBooking.getEndTime());
        assertEquals(booking.getUser().getId(), testBooking.getUser().getId());
    }

    @Test
    void testDeleteById() {
        Integer initialSize = bookingRepository.findAllBookings().size();
        Booking booking = bookingRepository.save(testBooking);
        Booking deleteBooking = bookingRepository.deleteById(booking.getId());
        Integer currentSize = bookingRepository.findAllBookings().size();

        assertNotNull(deleteBooking);
        assertEquals(initialSize, currentSize);
    }

    @Test
    void testFindById() {
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
    void testFindAllBookingsByDate() {
        bookingRepository.save(testBooking);
        Booking newBooking = Booking.builder()
                .startTime(LocalDateTime.of(2024,8,21,12,0,0))
                .endTime(LocalDateTime.of(2024,8,21,13,0,0))
                .user(testUserWithId)
                .build();
        bookingRepository.save(newBooking);
        List<Booking> bookings = bookingRepository.findAllBookingsByDate(
                LocalDateTime.of(2024,8,21,12,12,0));

        assertEquals(1, bookings.size());
    }

//    @Test
//    void testFindAllBookingsByUser() {
////        User newUserWithId = userRepository.save("new", "new");
//        bookingRepository.save(testBooking);
//        Booking newBooking = Booking.builder()
//                .startTime(LocalDateTime.of(2024,8,21,12,0,0))
//                .endTime(LocalDateTime.of(2024,8,21,13,0,0))
//                .user(testUserWithId)
//                .build();
//        bookingRepository.save(newBooking);
//
//        assertEquals(bookingRepository.findAllBookings().size(), 2);
//        List<Booking> bookings = bookingRepository.findAllBookingsByUser(testUserWithId.getUsername());
//
//        assertEquals(1, bookings.size());
//    }

    @Test
    void testFindAllBookingsByResource() {
        bookingRepository.save(testBooking);
        List<Booking> bookings = bookingRepository.findAllBookingsByResource(testWorkplaceWithId.getId());

        assertEquals(bookings.size(), 5);
    }

    @Test
    void testFindAllBookings() {
        bookingRepository.save(testBooking);
        Map<Integer, Booking> bookings = bookingRepository.findAllBookings();

        assertEquals(bookings.size(), 2);
    }
}