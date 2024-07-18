package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.ConferenceHall;
import org.example.mapper.ConferenceHallMapper;
import org.example.model.ConferenceHallDTO;
import org.example.service.ConferenceHallService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ConferenceHallControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConferenceHallService conferenceHallService;

    @Mock
    private ConferenceHallMapper conferenceHallMapper;

    @InjectMocks
    private ConferenceHallController conferenceHallController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(conferenceHallController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetConferenceHallById() throws Exception {
        ConferenceHall conferenceHall = new ConferenceHall();
        ConferenceHallDTO conferenceHallDTO = new ConferenceHallDTO();

        when(conferenceHallService.getConferenceHallById(anyString())).thenReturn(conferenceHall);
        when(conferenceHallMapper.conferenceHallToConferenceHallDTO(any())).thenReturn(conferenceHallDTO);

        mockMvc.perform(get("/conferenceHall/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllConferenceHalls() throws Exception {
        List<ConferenceHall> conferenceHalls = List.of(new ConferenceHall());
        List<ConferenceHallDTO> conferenceHallDTOs = List.of(new ConferenceHallDTO());

        when(conferenceHallService.getAllConferenceHalls()).thenReturn(conferenceHalls);
        when(conferenceHallMapper.conferenceHallsToConferenceHallDTOs(any())).thenReturn(conferenceHallDTOs);

        mockMvc.perform(get("/conferenceHall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateConferenceHall() throws Exception {
        ConferenceHallDTO conferenceHallDTO = new ConferenceHallDTO();
        ConferenceHall conferenceHall = new ConferenceHall();

        when(conferenceHallService.createConferenceHall(any())).thenReturn(conferenceHall);
        when(conferenceHallMapper.conferenceHallToConferenceHallDTO(any())).thenReturn(conferenceHallDTO);

        mockMvc.perform(post("/conferenceHall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conferenceHallDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateConferenceHall() throws Exception {
        ConferenceHallDTO conferenceHallDTO = new ConferenceHallDTO();
        ConferenceHall conferenceHall = new ConferenceHall();

        when(conferenceHallService.updateConferenceHall(anyString(), any())).thenReturn(conferenceHall);
        when(conferenceHallMapper.conferenceHallToConferenceHallDTO(any())).thenReturn(conferenceHallDTO);

        mockMvc.perform(put("/conferenceHall/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conferenceHallDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteConferenceHall() throws Exception {
        ConferenceHall conferenceHall = new ConferenceHall();
        ConferenceHallDTO conferenceHallDTO = new ConferenceHallDTO();

        when(conferenceHallService.deleteConferenceHall(anyString())).thenReturn(conferenceHall);
        when(conferenceHallMapper.conferenceHallToConferenceHallDTO(any())).thenReturn(conferenceHallDTO);

        mockMvc.perform(delete("/conferenceHall/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}