package org.example.repository;

import org.example.entity.User;
import org.example.model.UserDTO;

import java.sql.SQLException;
import java.util.Map;

/** An interface for UserRepositoryJDBC **/
public interface UserRepository {

    User save(UserDTO userDTO) throws SQLException;

    User findByUsername(String username) throws SQLException;

    User findById(Integer userId) throws SQLException;

    Map<String, User> findAll() throws SQLException;
}
