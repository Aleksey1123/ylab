package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.repository.ConferenceHallRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConferenceHallServiceTest {

    private ConferenceHallService conferenceHallService;
    private ConferenceHallRepositoryJDBC conferenceHallRepository;

    @BeforeEach
    void setUp() {
        conferenceHallRepository = mock(ConferenceHallRepositoryJDBC.class);
        conferenceHallService = new ConferenceHallService();
        conferenceHallService.repository = conferenceHallRepository; // Use mock repository
    }

    @Test
    void testCreateConferenceHallSuccess() {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.save(anyString(), anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.createConferenceHall("Main Hall", "100");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).save(anyString(), anyInt());
    }

    @Test
    void testCreateConferenceHallInvalidSize() {
        ConferenceHall result = conferenceHallService.createConferenceHall("Main Hall", "invalid-size");
        assertNull(result);
        verify(conferenceHallRepository, times(0)).save(anyString(), anyInt());
    }

    @Test
    void testGetConferenceHallByIdSuccess() {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.findById(anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.getConferenceHallById("1");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetConferenceHallByIdNotFound() {
        when(conferenceHallRepository.findById(anyInt())).thenReturn(null);

        ConferenceHall result = conferenceHallService.getConferenceHallById("999");
        assertNull(result);
        verify(conferenceHallRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetConferenceHallByIdInvalidId() {
        ConferenceHall result = conferenceHallService.getConferenceHallById("invalid-id");
        assertNull(result);
        verify(conferenceHallRepository, times(0)).findById(anyInt());
    }

    @Test
    void testGetAllConferenceHalls() {
        List<ConferenceHall> halls = List.of(new ConferenceHall());
        when(conferenceHallRepository.findAll()).thenReturn(halls);

        List<ConferenceHall> result = conferenceHallService.getAllConferenceHalls();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(conferenceHallRepository, times(1)).findAll();
    }

    @Test
    void testUpdateConferenceHallSuccess() {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.findById(anyInt())).thenReturn(hall);
        when(conferenceHallRepository.update(anyInt(), anyString(), anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.updateConferenceHall("1", "Updated Hall", "150");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).findById(anyInt());
        verify(conferenceHallRepository, times(1)).update(anyInt(), anyString(), anyInt());
    }

    @Test
    void testUpdateConferenceHallNotFound() {
        when(conferenceHallRepository.findById(anyInt())).thenReturn(null);

        ConferenceHall result = conferenceHallService.updateConferenceHall("999", "Updated Hall", "150");
        assertNull(result);
        verify(conferenceHallRepository, times(1)).findById(anyInt());
        verify(conferenceHallRepository, times(0)).update(anyInt(), anyString(), anyInt());
    }

    @Test
    void testUpdateConferenceHallInvalidId() {
        ConferenceHall result = conferenceHallService.updateConferenceHall("invalid-id", "Updated Hall", "150");
        assertNull(result);
        verify(conferenceHallRepository, times(0)).findById(anyInt());
        verify(conferenceHallRepository, times(0)).update(anyInt(), anyString(), anyInt());
    }

    @Test
    void testDeleteConferenceHallSuccess() {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.deleteById(anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.deleteConferenceHall("1");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteConferenceHallNotFound() {
        when(conferenceHallRepository.deleteById(anyInt())).thenReturn(null);

        ConferenceHall result = conferenceHallService.deleteConferenceHall("999");
        assertNull(result);
        verify(conferenceHallRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteConferenceHallInvalidId() {
        ConferenceHall result = conferenceHallService.deleteConferenceHall("invalid-id");
        assertNull(result);
        verify(conferenceHallRepository, times(0)).deleteById(anyInt());
    }
}
