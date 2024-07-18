package org.example.repository;

import org.example.entity.User;
import org.example.exception.InvalidResponseBodyException;
import org.example.model.UserDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/** Repository for user entity with basic CRUD operations. **/
@Repository
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
    public User save(UserDTO userDTO) throws SQLException {

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
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
            else throw new InvalidResponseBodyException("Error creating user with posted data.");
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    /** This method returns an existing user from a database by specific username, if
     * the query has an error this method will output the sql exception. **/
    @Override
    public User findByUsername(String username) throws SQLException {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapUser(set);
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns an existing user from a database by specific id, if
     * the query has an error this method will output the sql exception. **/
    @Override
    public User findById(Integer userId) throws SQLException {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return mapUser(set);
            }

            return null;
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    /** This method returns all users from a database, if
     * the query has a mistake this method will output the sql exception. **/
    @Override
    public Map<String, User> findAll() throws SQLException {

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
            throw new SQLException(e.getMessage());
        }
    }
}
