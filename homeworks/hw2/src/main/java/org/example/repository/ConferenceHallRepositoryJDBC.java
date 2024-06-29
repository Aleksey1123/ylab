package org.example.repository;

import org.example.entity.ConferenceHall;
import org.example.enums.Resource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ConferenceHallRepositoryJDBC implements ConferenceHallRepository {

    private final String resourceType = Resource.HALL.toString();

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://db:5432/efficient_work?currentSchema=service_schema";
//        String url = "jdbc:postgresql://db:5432/efficient_work";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    private ConferenceHall mapConferenceHall(ResultSet set) throws SQLException {
        UUID id = set.getObject("id", UUID.class);
        String description = set.getString("description");
        int size = set.getInt("size");

        return ConferenceHall.builder()
                .id(id)
                .description(description)
                .size(size)
                .build();
    }

    @Override
    public ConferenceHall save(String description, Integer size) {

        String sql = "INSERT INTO conference_halls (id, description, size) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            UUID id = UUID.randomUUID();
            statement.setObject(1, id);
            statement.setString(2, description);
            statement.setObject(3, size);

            int rowsSaved = statement.executeUpdate();
            if (rowsSaved == 0)
                throw new SQLException("Creation of the conference hall failed, try again.");

            return ConferenceHall.builder()
                    .id(id)
                    .description(description)
                    .size(size)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    public ConferenceHall update(String id, String description, int size) {

        String sql = "UPDATE conference_halls SET description = ?, size = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, size);
            statement.setObject(3, UUID.fromString(id));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Update of the conference hall failed, no rows affected.");
            }

            return ConferenceHall.builder()
                    .id(UUID.fromString(id))
                    .size(size)
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ConferenceHall findById(String hallId) {

        String sql = "SELECT * FROM conference_halls WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, UUID.fromString(hallId));
            ResultSet set = statement.executeQuery();

            if (set.next())
                return mapConferenceHall(set);

            throw new RuntimeException("Such conference hall does not exist.");
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<ConferenceHall> findAll() {

        String sql = "SELECT * FROM conference_halls";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<ConferenceHall> halls = new ArrayList<>();
            while (resultSet.next()) {
                halls.add(mapConferenceHall(resultSet));
            }

            return halls;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ConferenceHall deleteById(String hallId) {

        ConferenceHall hall = findById(hallId);
        if (hall == null) {
            throw new NoSuchElementException("Conference hall with id" + hallId + " not found.");
        }

        String sql = "DELETE FROM conference_halls WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, UUID.fromString(hallId));
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SQLException("Deletion of the conference hall failed, no rows affected.");
            }

            return hall;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }
}

