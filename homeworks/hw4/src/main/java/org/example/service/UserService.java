package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.*;
import org.example.model.UserDTO;
import org.example.repository.UserRepositoryJDBC;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Map;

/** This service corresponds for work with User Entities. **/
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryJDBC repository;
    protected User authorisedUser;

    /** Creates a new user with a specific username and password. Throws a RuntimeException
     * if username is null or if such username is already exists.**/
    public User addUser(UserDTO userDTO) throws SQLException {

        String username = userDTO.getUsername();
        if (username.isEmpty()) {
            throw new InvalidUsernameException("Username must be not empty.");
        }

        boolean conflict = repository.findAll().values()
                .stream()
                .anyMatch(u -> u.getUsername().equals(username));
        if (conflict) {
            throw new UsernameAlreadyExistsException("Such username already exists.");
        }

        return repository.save(userDTO);
    }

    /**
     * Authorizes the user. Throws a RuntimeException in cases: if the user is already logged in,
     * if the username is incorrect. Returns true if the authorisation ends up successful otherwise - false.
     */
    public User authorizeUser(UserDTO userDTO) throws SQLException {

        if (authorisedUser != null) {
            throw new AlreadyLoggedInException("You are already logged in as: " +
                    authorisedUser.getUsername() + ".");
        }

        User foundUser = repository.findByUsername(userDTO.getUsername());
        if (foundUser == null || !foundUser.getPassword().equals(userDTO.getPassword())) {
            throw new InvalidCredentialsException("Incorrect username or password, please try again!");
        }
        authorisedUser = foundUser;

        return authorisedUser;
    }

    /**
     * Logout by changing the authorisedUser variable.
     * Method throws a RuntimeException in case the user is not logged in (authorisedUser == null).
     */
    public boolean logOut() {

        if (authorisedUser == null) {
            throw new NotAuthorizedException("You are not logged in.");
        }
        authorisedUser = null;

        return true;
    }

    public User isAuthorised() {

        return authorisedUser;
    }

    /** Outputs all registered users. **/
    public Map<String, User> getAllUsers() throws SQLException {

        return repository.findAll();
    }

    /** Outputs the user. **/
    public User findUserByUsername(String username) throws SQLException {

        return repository.findByUsername(username);
    }
}
