package org.example.repository;

import org.example.entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepositoryJDBC implements UserRepository {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://db:5432/efficient_work?currentSchema=service_schema";
//        String url = "jdbc:postgresql://db:5432/efficient_work";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    private User mapUser(ResultSet set) throws SQLException {
        UUID id = set.getObject("id", UUID.class);
        String username = set.getString("username");
        String password = set.getString("password");

        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public User save(String username, String password) {

        String sql = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            UUID id = UUID.randomUUID();
            statement.setObject(1, id);
            statement.setString(2, username);
            statement.setString(3, password);

            int rowsSaved = statement.executeUpdate();
            if (rowsSaved == 0) {
                throw new SQLException("Creation of the user failed, try again.");
            }

            return User.builder()
                    .id(id)
                    .username(username)
                    .password(password)
                    .build();
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    @Override
    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapUser(set);
            }

            throw new RuntimeException("User with username " + username + " not found.");
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public User findById(String id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, UUID.fromString(id));
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapUser(set);
            }

            throw new RuntimeException("User with id " + id + " not found.");
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
    public Map<String, User> findAll() {

        String sql = "SELECT * FROM users";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            Map<String, User> users = new HashMap<>();
            while (resultSet.next()) {
                User user = mapUser(resultSet);
                users.put(user.getId().toString(), user);
            }

            return users;
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }
}
