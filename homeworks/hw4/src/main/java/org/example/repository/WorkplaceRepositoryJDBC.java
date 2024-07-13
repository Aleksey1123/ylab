package org.example.repository;

import org.example.entity.Workplace;
import org.example.exception.EntityNotFoundException;
import org.example.model.WorkplaceDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Repository for workplace entity with basic CRUD operations. **/
@Repository
public class WorkplaceRepositoryJDBC implements WorkplaceRepository {

    /** This method manages the connection between app and database. **/
    protected Connection getConnection() throws SQLException {
        // if we connect from app, which located in a docker container we use this as a
        // url: "jdbc:postgresql:/db:5432/efficient_work?currentSchema=service_schema"
        String url = "jdbc:postgresql://localhost:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    /** This method takes result set and returns a new created workplace. **/
    private Workplace mapWorkplace(ResultSet set) throws SQLException {
        int workplaceId = set.getInt("id");
        String description = set.getString("description");

        return Workplace.builder()
                .id(workplaceId)
                .description(description)
                .build();
    }

    /** This method saves workplace entity to a database. **/
    @Override
    public Workplace save(WorkplaceDTO workplaceDTO) throws SQLException {

        String description = workplaceDTO.getDescription();
        String sql = "INSERT INTO workplaces (description) VALUES (?) RETURNING id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int workplaceId = resultSet.getInt(1);
                return Workplace.builder()
                        .id(workplaceId)
                        .description(description)
                        .build();
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method updates an existing workplace from a database by specific id, if
     * such workplace doesn't exist method will output the error. **/
    @Override
    public Workplace update(Integer workplaceId, String description) throws SQLException {

        String sql = "UPDATE workplaces SET description = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, workplaceId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new EntityNotFoundException("Workplace with id: " + workplaceId + " not found.");
            }

            return Workplace.builder()
                    .id(workplaceId)
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /** This method returns an existing workplace from a database by specific id, if
     * the query has an error this method will output the sql exception. **/
    @Override
    public Workplace findById(Integer workplaceId) throws SQLException {

        String sql = "SELECT * FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workplaceId);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapWorkplace(set);
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all workplaces from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public List<Workplace> findAll() throws SQLException {

        String sql = "SELECT * FROM workplaces";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Workplace> workplaces = new ArrayList<>();
            while (resultSet.next()) {
                workplaces.add(mapWorkplace(resultSet));
            }

            return workplaces;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method deletes an existing workplace from a database by specific id, if
     * such workplace doesn't exist method will output the error. **/
    @Override
    public Workplace deleteById(Integer workplaceId) throws SQLException {

        String sql = "DELETE FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, workplaceId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new EntityNotFoundException("Workplace with id " + workplaceId + " not found.");
            }

            return findById(workplaceId);
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
