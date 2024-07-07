package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class UserServletTest {

    private UserService userService;
    private UserServlet userServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter responseWriter;

    private final String username = "testUser";

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userServlet = new UserServlet();
        UserServlet.userService = userService;
        userServlet.userMapper = UserMapper.INSTANCE;
        userServlet.objectMapper = new ObjectMapper();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        responseWriter = new StringWriter();
    }

    @Test
    void testDoGetAllUsers() throws Exception {
        when(request.getParameter("username")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = responseWriter.toString();
        assertFalse(jsonResponse.isEmpty());
    }

    @Test
    void testDoGetUserByUsername() throws Exception {

        when(request.getParameter("username")).thenReturn(username);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        User user = User.builder()
                .username(username)
                .password("password")
                .build();

        when(userService.findUserByUsername(username)).thenReturn(user);

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        String jsonResponse = responseWriter.toString();
        assertFalse(jsonResponse.isEmpty());
    }

    @Test
    void testDoDeleteLogout() throws Exception {
        when(request.getParameter("action")).thenReturn("logout");
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        userServlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}