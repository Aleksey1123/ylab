package org.example.repository;

import org.example.entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryJDBC implements UserRepository {

    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://db:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    private User mapUser(ResultSet set) throws SQLException {
        int userId = set.getInt("id");
        String username = set.getString("username");
        String password = set.getString("password");

        return User.builder()
                .id(userId)
                .username(username)
                .password(password)
                .build();
    }

    @Override
    public User save(String username, String password) {

        String sql = "INSERT INTO users (username, password) VALUES (?, ?) RETURNING id";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                return User.builder()
                        .id(userId)
                        .username(username)
                        .password(password)
                        .build();
            }
            else throw new SQLException("Creation of the user failed, try again.");
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

            if (set.next())
                return mapUser(set);
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
            return null;
        }

        System.out.println("User with username " + username + " not found.");
        return null;
    }

    @Override
    public User findById(Integer userId) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();

            if (set.next())
                return mapUser(set);
        }
        catch (SQLException e) {
            System.out.println("SQL exception occurred: " + e.getMessage());
            return null;
        }

        System.out.println("User with id " + userId + " not found.");
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
