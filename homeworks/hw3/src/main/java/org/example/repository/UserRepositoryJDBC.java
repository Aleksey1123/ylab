package org.example.repository;

import org.example.entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/** Repository for user entity with basic CRUD operations. **/
public class UserRepositoryJDBC implements UserRepository {

    /** This method manages the connection between app and database. **/
    public Connection getConnection() throws SQLException {
        // if we connect from app, which located in a docker container we use this as a
        // url: "jdbc:postgresql:/db:5432/efficient_work?currentSchema=service_schema"
        String url = "jdbc:postgresql://localhost:5432/efficient_work?currentSchema=service_schema";
        String user = "root";
        String password = "password";

        return DriverManager.getConnection(url, user, password);
    }

    /** This method takes result set and returns a new created user. **/
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

    /** This method saves user entity to a database. **/
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

    /** This method returns an existing user from a database by specific username, if
     * the query has an error this method will output the sql exception. **/
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

    /** This method returns an existing user from a database by specific id, if
     * the query has an error this method will output the sql exception. **/
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

    /** This method returns all users from a database, if
     * the query has a mistake this method will output the sql exception. **/
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
