package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.ConferenceHall;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InvalidResponseBodyException;
import org.example.model.ConferenceHallDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Repository for hall entity with basic CRUD operations. **/
@Repository
public class ConferenceHallRepositoryJDBC implements ConferenceHallRepository {

    /** This method manages the connection between app and database. **/
    protected Connection getConnection() throws SQLException {
        // if we connect from app, which located in a docker container we use this as a
        // url: "jdbc:postgresql:/db:5432/efficient_work?currentSchema=service_schema"
        String url = "jdbc:postgresql://localhost:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    /** This method takes result set and returns a new created hall. **/
    private ConferenceHall mapConferenceHall(ResultSet set) throws SQLException {
        Integer hallId = set.getInt("id");
        String description = set.getString("description");
        int size = set.getInt("size");

        return ConferenceHall.builder()
                .id(hallId)
                .description(description)
                .size(size)
                .build();
    }

    /** This method saves hall entity to a database. **/
    @Override
    public ConferenceHall save(ConferenceHallDTO conferenceHallDTO) throws SQLException {

        String description = conferenceHallDTO.getDescription();
        int size = Integer.parseInt(conferenceHallDTO.getSize());
        String sql = "INSERT INTO conference_halls (description, size) VALUES (?, ?) RETURNING id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setObject(2, size);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int hallId = resultSet.getInt(1);
                return ConferenceHall.builder()
                        .id(hallId)
                        .description(description)
                        .size(size)
                        .build();
            }
            else throw new InvalidResponseBodyException("Error creating conference-hall with posted data.");
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method updates an existing hall from a database by specific id, if
     * such hall doesn't exist method will output the error. **/
    @Override
    public ConferenceHall update(Integer hallId, String description, int size) throws SQLException {

        String sql = "UPDATE conference_halls SET description = ?, size = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, size);
            statement.setInt(3, hallId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new EntityNotFoundException("Conference-hall with id: " + hallId + " not found.");
            }

            return ConferenceHall.builder()
                    .id(hallId)
                    .size(size)
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns an existing hall from a database by specific id, if
     * the query has an error this method will output the sql exception. **/
    @Override
    public ConferenceHall findById(Integer hallId) throws SQLException {

        String sql = "SELECT * FROM conference_halls WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, hallId);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapConferenceHall(set);
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all halls from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public List<ConferenceHall> findAll() throws SQLException {

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
            throw new SQLException(e.getMessage());
        }
    }

    /** This method deletes an existing hall from a database by specific id, if
     * such hall doesn't exist method will output the error. **/
    @Override
    public ConferenceHall deleteById(Integer hallId) throws SQLException {

        String sql = "DELETE FROM conference_halls WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, hallId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new EntityNotFoundException("Conference hall with id" + hallId + " not found.");
            }

            return findById(hallId);
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}

