package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepositoryJDBC;

import java.util.Map;

/** This service corresponds for work with User Entities. **/
public class UserService {

    private UserRepositoryJDBC repository;
    private User authorisedUser;

    public UserService() {
        repository = new UserRepositoryJDBC();
    }

    public User addUser(String username, String password) {

        try {
            if (username.isEmpty())
                throw new RuntimeException("Username must be not empty.");

            boolean conflict = repository.findAll().values()
                    .stream()
                    .anyMatch(u -> u.getUsername().equals(username));
            if (conflict) {
                throw new RuntimeException("Such username already exists.\n" +
                        "Enter a new username.");
            }

//            else throw new RuntimeException("An error occurred, try again!");
            return repository.save(username, password);
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }

    /**
     * Authorizes the user. Throws a RuntimeException in cases: if the user is already logged in,
     * if the username is incorrect. Returns true if the authorisation ends up successful otherwise - false.
     */
    public boolean authorizeUser(String username, String password) {

        try {
            if (authorisedUser != null)
                throw new RuntimeException("You are already logged in as: " +
                        authorisedUser.getUsername() + ".");

            User foundUser = repository.findByUsername(username);
            if (foundUser == null || !foundUser.getPassword().equals(password)) {
                throw new RuntimeException("Incorrect username or password, please try again!");
            }
            authorisedUser = foundUser;

            return true;
        }
        catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    /**
     * Logout by changing the authorisedUser variable.
     * Method throws a RuntimeException in case the user is not logged in (authorisedUser == null).
     */
    public boolean logOut() {
        try {

            if (authorisedUser == null)
                throw new RuntimeException("You are not logged in!");

            authorisedUser = null;

            return true;
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    public User isAuthorised() {

        return authorisedUser;
    }

    /** Outputs all registered users. **/
    public Map<String, User> getAllUsers() {

        return repository.findAll();
    }

    /** Outputs the user. **/
    public User findUserByUsername(String username) {

        return repository.findByUsername(username);
    }
}
