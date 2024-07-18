package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.Booking;
import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InvalidResponseBodyException;
import org.example.exception.InvalidUsernameException;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/** Repository for booking entity with basic CRUD operations. **/
@Repository
@RequiredArgsConstructor
public class BookingRepositoryJDBC implements BookingRepository {

    private final UserRepository userRepository;

    /** This method manages the connection between app and database. **/
    protected Connection getConnection() throws SQLException {
        // if we connect from app, which located in a docker container we use this as a
        // url: "jdbc:postgresql:/db:5432/efficient_work?currentSchema=service_schema"
        String url = "jdbc:postgresql://localhost:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    /** This method takes result set and returns a new created booking. **/
    private Booking mapBooking(ResultSet set) throws SQLException {
        int bookingId = set.getInt("id");
        Integer workplaceId = set.getInt("workplace_id");
        Integer hallId = set.getInt("conference_hall_id");
        LocalDateTime startTime = set.getTimestamp("start_time").toLocalDateTime();
        LocalDateTime endTime = set.getTimestamp("end_time").toLocalDateTime();
        int userId = set.getInt("user_id");
        String username = set.getString("username");
        String password = set.getString("password");

        User user = User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();

        Booking.BookingBuilder bookingBuilder = Booking.builder()
                .id(bookingId)
                .startTime(startTime)
                .endTime(endTime)
                .user(user);

        if (workplaceId != null) {
            bookingBuilder.workplaceId(workplaceId);
        }
        else if (hallId != null) {
            bookingBuilder.hallId(hallId);
        }

        return bookingBuilder.build();
    }


    /** This method saves Booking entity to a database. **/
    @Override
    public Booking save(Booking booking) throws SQLException {

        String sql = "INSERT INTO bookings (workplace_id, conference_hall_id, start_time," +
                " end_time, user_id) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (booking.getWorkplaceId() != null) {
                statement.setObject(1, booking.getWorkplaceId());
                statement.setNull(2, Types.OTHER);
            }
            else {
                statement.setNull(1, Types.OTHER);
                statement.setObject(2, booking.getHallId());
            }
            statement.setTimestamp(3, Timestamp.valueOf(booking.getStartTime()));
            statement.setTimestamp(4, Timestamp.valueOf(booking.getEndTime()));
            statement.setObject(5, booking.getUser().getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int bookingId = resultSet.getInt(1);
                return Booking.builder()
                        .id(bookingId)
                        .workplaceId(booking.getWorkplaceId())
                        .hallId(booking.getHallId())
                        .startTime(booking.getStartTime())
                        .endTime(booking.getEndTime())
                        .user(booking.getUser())
                        .build();
            }
            else throw new InvalidResponseBodyException("Error creating user with posted data.");
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method deletes an existing booking from a database by specific id, if
     * such booking doesn't exist method will output the error. **/
    @Override
    public Booking deleteById(Integer bookingId) throws SQLException {

        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new EntityNotFoundException("Booking with id: " + bookingId + " not found.");
            }

            return findById(bookingId);
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /** This method returns an existing booking from a database by specific id, if
     * the query has an error this method will output the sql exception. **/
    public Booking findById(Integer bookingId) throws SQLException {

        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id WHERE b.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapBooking(resultSet);
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all bookings filtered by a specific date from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public List<Booking> findAllBookingsByDate(LocalDateTime date) throws SQLException {

        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id WHERE DATE(b.start_time) = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(date.toLocalDate()));
            ResultSet resultSet = statement.executeQuery();

            List<Booking> bookings = new ArrayList<>();
            while (resultSet.next()) {
                bookings.add(mapBooking(resultSet));
            }

            return bookings;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all bookings filtered by a specific user from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public List<Booking> findAllBookingsByUser(String username) throws SQLException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new InvalidUsernameException("User with username " + username + " not found.");
        }

        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id WHERE u.username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            List<Booking> bookings = new ArrayList<>();
            while (resultSet.next()) {
                bookings.add(mapBooking(resultSet));
            }

            return bookings;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all bookings from a database filtered by a specific resource, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public List<Booking> findAllBookingsByResource(Integer resourceId) throws SQLException {

        int resourceIdInt = resourceId;
        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id WHERE (b.workplace_id = ? OR b.conference_hall_id = ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, resourceIdInt);
            statement.setInt(2, resourceIdInt);
            ResultSet resultSet = statement.executeQuery();

            List<Booking> bookings = new ArrayList<>();
            while (resultSet.next()) {
                bookings.add(mapBooking(resultSet));
            }

            return bookings;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all bookings from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public Map<Integer, Booking> findAllBookings() throws SQLException {

        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            Map<Integer, Booking> bookings = new HashMap<>();
            while (resultSet.next()) {
                Booking booking = mapBooking(resultSet);
                bookings.put(booking.getId(), booking);
            }

            return bookings;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}

