package org.example.repository;

import org.example.entity.Booking;
import org.example.entity.User;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class BookingRepositoryJDBC implements BookingRepository {

    private UserRepository userRepository;

    public BookingRepositoryJDBC() {
        userRepository = new UserRepositoryJDBC();
    }

    protected Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://db:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

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

        if (workplaceId != null)
            bookingBuilder.workplaceId(workplaceId);
        else if (hallId != null)
            bookingBuilder.hallId(hallId);

        return bookingBuilder.build();
    }


    @Override
    public Booking save(Booking booking) {

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

        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Booking deleteById(Integer bookingId) {

        Booking booking = findById(bookingId);
        if (booking == null) {
            System.out.println("Booking with id " + bookingId + " not found.");
            return null;
        }

        String sql = "DELETE FROM bookings WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("Deletion of the booking failed, no rows affected.");
                return null;
            }

            return booking;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    public Booking findById(Integer bookingId) {

        String sql = "SELECT b.*, u.username, u.password FROM bookings b JOIN users u" +
                " ON b.user_id = u.id WHERE b.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return mapBooking(resultSet);
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Booking> findAllBookingsByDate(LocalDateTime date) {

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
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Booking> findAllBookingsByUser(String username) {

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new NoSuchElementException("User with username " + username + " not found.");

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
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Booking> findAllBookingsByResource(Integer resourceId) {

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
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Map<Integer, Booking> findAllBookings() {

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
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }
}

