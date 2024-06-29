package org.example.repository;

import org.example.entity.Workplace;
import org.example.enums.Resource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class WorkplaceRepositoryJDBC implements WorkplaceRepository {

    private final String resourceType = Resource.WORKPLACE.toString();

    private Connection getConnection() throws SQLException {
//        String url = "jdbc:postgresql://db:5432/efficient_work";
        String url = "jdbc:postgresql://db:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    private Workplace mapWorkplace(ResultSet set) throws SQLException {
        UUID id = set.getObject("id", UUID.class);
        String description = set.getString("description");

        return Workplace.builder()
                .id(id)
                .description(description)
                .build();
    }

    @Override
    public Workplace save(String description) {

        String sql = "INSERT INTO workplaces (id, description) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            UUID id = UUID.randomUUID();
            statement.setObject(1, id);
            statement.setString(2, description);

            int rowsSaved = statement.executeUpdate();
            if (rowsSaved == 0) {
                throw new SQLException("Creation of the workplace failed, try again.");
            }

            return Workplace.builder()
                    .id(id)
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    public Workplace update(String id, String description) {

        String sql = "UPDATE workplaces SET description = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setObject(2, UUID.fromString(id));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Update of the workplace failed, no rows affected.");
            }

            return Workplace.builder()
                    .id(UUID.fromString(id))
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Workplace findById(String workplaceId) {

        String sql = "SELECT * FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, UUID.fromString(workplaceId));
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapWorkplace(set);
            }

            throw new RuntimeException("Such workplace does not exist.");
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
    public List<Workplace> findAll() {

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
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Workplace deleteById(String workplaceId) {

        Workplace workplace = findById(workplaceId);
        if (workplace == null) {
            throw new NoSuchElementException("Workplace with id " + workplaceId + " not found.");
        }

        String sql = "DELETE FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, UUID.fromString(workplaceId));
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SQLException("Deletion of the workplace failed, no rows affected.");
            }

            return workplace;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }
}
