package org.example.repository;

import org.example.entity.Workplace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkplaceRepositoryJDBC implements WorkplaceRepository {

    protected Connection getConnection() throws SQLException {
        // if we connect from app, which located in a docker container we use this as a
        // url: "jdbc:postgresql:/db:5432/efficient_work?currentSchema=service_schema"
        String url = "jdbc:postgresql://localhost:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    private Workplace mapWorkplace(ResultSet set) throws SQLException {
        int workplaceId = set.getInt("id");
        String description = set.getString("description");

        return Workplace.builder()
                .id(workplaceId)
                .description(description)
                .build();
    }

    @Override
    public Workplace save(String description) {

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
            else System.out.println("Creation of the workplace failed, try again.");
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Workplace update(Integer workplaceId, String description) {

        String sql = "UPDATE workplaces SET description = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, description);
            statement.setInt(2, workplaceId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Update of the workplace failed, no rows affected.");
                return null;
            }

            return Workplace.builder()
                    .id(workplaceId)
                    .description(description)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Workplace findById(Integer workplaceId) {

        String sql = "SELECT * FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workplaceId);
            ResultSet set = statement.executeQuery();

            if (set.next())
                return mapWorkplace(set);
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
            return null;
        }

        System.out.println("Such workplace does not exist.");
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
    public Workplace deleteById(Integer workplaceId) {

        Workplace workplace = findById(workplaceId);
        if (workplace == null) {
            System.out.println("Workplace with id " + workplaceId + " not found.");
            return null;
        }

        String sql = "DELETE FROM workplaces WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, workplaceId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("Deletion of the workplace failed, no rows affected.");
                return null;
            }

            return workplace;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }
}
