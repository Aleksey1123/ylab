package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.model.UserDTO;
import org.example.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Aspect
@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    public static UserService userService = new UserService();
    protected UserMapper userMapper = UserMapper.INSTANCE;
    protected ObjectMapper objectMapper = new ObjectMapper();

    /** This method returns a user specified by username parameter or returns
     * all registered users if username parameter is null.
     * Requested Parameter:
     * ?username=******* - id of any existing user
     * or it will return all existing users
     *
     * **/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");

        if (username != null)
            findByUsername(resp, username);
        else findAll(resp);
    }

    /** This method are used by doGet(). It returns an existing user specified by username. **/
    private void findByUsername(HttpServletResponse resp, String username) throws IOException {

        User user = userService.findUserByUsername(username);
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Incorrect username: " + username + "\"}");
        }
        else {
            UserDTO userDTO = userMapper.userToUserDTO(user);
            String userString = objectMapper.writeValueAsString(userDTO);
            printJson(resp, userString);
        }
    }

    /** This method are used by doGet(). It returns a json of all users. **/
    private void findAll(HttpServletResponse resp) throws IOException {

        List<User> users = new ArrayList<>(userService.getAllUsers().values());
        List<UserDTO> userDTOs =
                userMapper.usersToUserDTOs(users);
        String userString = objectMapper.writeValueAsString(userDTOs);
        printJson(resp, userString);
    }

    /** This method returns a json of a given string. **/
    private void printJson(HttpServletResponse resp, String stringObject) throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(stringObject);
        out.flush();
    }

    /** This method creates a new user via request body data.
     *  Requested Body:
     * {
     *     "username": ******* - username of an existing person
     *     "password": &&&&&&& - password of that user
     * }
     *
     * **/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO =
                objectMapper.readValue(req.getInputStream(), UserDTO.class);
        User savedUser =
                userService.addUser(userDTO.getUsername(), userDTO.getPassword());
        if (savedUser != null) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), savedUser);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Incorrect user data: "
                    + userDTO.getUsername()
                    + ", "
                    + userDTO.getPassword()
                    + "\"}");
        }
    }

    /** This method authorizes the user.
     * Requested Body:
     * {
     *     "username": ******* - username of an existing person
     *     "password": &&&&&&& - password of that user
     * }
     *
     * **/
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO =
                objectMapper.readValue(req.getInputStream(), UserDTO.class);
        User user = userMapper.userDTOToUser(userDTO);
        if (userService.authorizeUser(user)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Incorrect user data: "
                    + userDTO.getUsername()
                    + ", "
                    + userDTO.getPassword()
                    + "\"}");
        }
    }

    /** This method deletes a user specified by id.
     * Requested Parameter:
     * ?action=******* - can be logout otherwise will throw error
     *
     * **/
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action.equals("logout")) {
            userService.logOut();
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printJson(resp, "{\"error\":\"Incorrect action. Logout failed.\"}");
        }
    }
}
