package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Booking;
import org.example.mapper.BookingMapper;
import org.example.model.BookingDTO;
import org.example.model.BookingPostRequest;
import org.example.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllBookings() throws Exception {

        mockMvc.perform(get("/booking?action=all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAddBooking() throws Exception {
        BookingPostRequest bookingRequest = new BookingPostRequest();
        bookingRequest.setResourceType("W");
        bookingRequest.setResourceId("1");
        bookingRequest.setStartDateTimeString("2024-06-21T15:00:00");
        bookingRequest.setEndDateTimeString("2024-06-21T16:00:00");

        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();

        when(bookingService.makeBooking(any())).thenReturn(booking);
        when(bookingMapper.bookingToBookingDTO(any())).thenReturn(bookingDTO);

        mockMvc.perform(post("/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
    }
}