package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.model.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetUserByUsername() throws Exception {
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userService.findUserByUsername(anyString())).thenReturn(user);
        when(userMapper.userToUserDTO(any())).thenReturn(userDTO);

        mockMvc.perform(get("/user/{username}", "john_doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = List.of(new User());
        List<UserDTO> userDTOs = List.of(new UserDTO());

        when(userService.getAllUsers()).thenReturn(Map.of("1", new User()));
        when(userMapper.usersToUserDTOs(any())).thenReturn(userDTOs);

        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(userService.addUser(any())).thenReturn(user);
        when(userMapper.userToUserDTO(any())).thenReturn(userDTO);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthorizeUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        user.setUsername("john_doe");

        when(userService.authorizeUser(any())).thenReturn(user);

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Authorized as: john_doe."));
    }

    @Test
    void testLogOut() throws Exception {
        mockMvc.perform(delete("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully logged out."));
    }
}