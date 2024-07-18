package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.example.repository.ConferenceHallRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConferenceHallServiceTest {

    @Mock
    private ConferenceHallRepositoryJDBC conferenceHallRepository;
    
    @InjectMocks
    private ConferenceHallService conferenceHallService;
    
    private ConferenceHallDTO testConferenceHallDTO = ConferenceHallDTO.builder()
            .description("Test Hall")
            .size("120")
            .build();

    @Test
    void testCreateConferenceHallSuccess() throws SQLException {
        when(conferenceHallRepository.save(any(ConferenceHallDTO.class))).thenReturn(new ConferenceHall());
        ConferenceHall conferenceHall = conferenceHallService.createConferenceHall(testConferenceHallDTO);

        assertNotNull(conferenceHall);
    }

    @Test
    void testGetConferenceHallByIdSuccess() throws SQLException {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.findById(anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.getConferenceHallById("1");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).findById(anyInt());
    }



    @Test
    void testGetAllConferenceHalls() throws SQLException {
        List<ConferenceHall> halls = List.of(new ConferenceHall());
        when(conferenceHallRepository.findAll()).thenReturn(halls);

        List<ConferenceHall> result = conferenceHallService.getAllConferenceHalls();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(conferenceHallRepository, times(1)).findAll();
    }

    @Test
    void testUpdateConferenceHallSuccess() throws SQLException {
        when(conferenceHallRepository.update(anyInt(), anyString(), anyInt())).thenReturn(new ConferenceHall());

        ConferenceHall result = conferenceHallService.updateConferenceHall("1", testConferenceHallDTO);
        assertNotNull(result);
    }


    @Test
    void testDeleteConferenceHallSuccess() throws SQLException {
        ConferenceHall hall = new ConferenceHall();
        when(conferenceHallRepository.deleteById(anyInt())).thenReturn(hall);

        ConferenceHall result = conferenceHallService.deleteConferenceHall("1");
        assertNotNull(result);
        verify(conferenceHallRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteConferenceHallNotFound() throws SQLException {
        when(conferenceHallRepository.deleteById(anyInt())).thenReturn(null);

        ConferenceHall result = conferenceHallService.deleteConferenceHall("999");
        assertNull(result);
        verify(conferenceHallRepository, times(1)).deleteById(anyInt());
    }
}
