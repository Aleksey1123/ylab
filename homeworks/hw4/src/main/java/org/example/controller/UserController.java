package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.model.UserDTO;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private ResponseEntity<UserDTO> mapUser(User user) {

        UserDTO userDTO = userMapper.userToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    /** This method returns a user specified by username parameter or returns
     * all registered users if username parameter is null.
     * Requested Parameter:
     * ?username=******* - id of any existing user
     * or it will return all existing users
     *
     * **/
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable("username") String username) throws SQLException {

        User user = userService.findUserByUsername(username);
        return mapUser(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>(userService.getAllUsers().values());
        List<UserDTO> userDTOs = userMapper.usersToUserDTOs(users);
        return ResponseEntity.ok(userDTOs);
    }


    /** This method creates a new user via request body data.
     *  Requested Body:
     * {
     *     "username": ******* - username of an existing person
     *     "password": &&&&&&& - password of that user
     * }
     *
     * **/
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws SQLException {

        User savedUser = userService.addUser(userDTO);
        return mapUser(savedUser);
    }

    /** This method authorizes the user.
     * Requested Body:
     * {
     *     "username": ******* - username of an existing person
     *     "password": &&&&&&& - password of that user
     * }
     *
     * **/
    @PutMapping
    public ResponseEntity<String> authorizeUser(@RequestBody UserDTO userDTO) throws SQLException {

        User user = userService.authorizeUser(userDTO);
        return ResponseEntity.ok().body("Authorized as: " + user.getUsername() + ".");
    }

    /** This method deletes a user specified by id.
     * Requested Parameter:
     * ?action=******* - can be logout otherwise will throw error
     *
     * **/
    @DeleteMapping
    public ResponseEntity<String> logOut() {

        userService.logOut();
        return ResponseEntity.ok().body("Successfully logged out.");
    }
}
