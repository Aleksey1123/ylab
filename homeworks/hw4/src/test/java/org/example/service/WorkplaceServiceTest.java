package org.example.service;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;
import org.example.repository.WorkplaceRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class WorkplaceServiceTest {

    @Mock
    private WorkplaceRepositoryJDBC workplaceRepository;

    @InjectMocks
    private WorkplaceService workplaceService;

    private WorkplaceDTO workplaceDTO = WorkplaceDTO.builder()
            .description("test")
            .build();

    @Test
    void testCreateWorkplaceSuccess() throws SQLException {
        when(workplaceRepository.save(any(WorkplaceDTO.class))).thenReturn(new Workplace());

        Workplace result = workplaceService.createWorkplace(workplaceDTO);
        assertNotNull(result);
    }

    @Test
    void testGetWorkplaceByIdSuccess() throws SQLException {
        Workplace workplace = new Workplace();
        when(workplaceRepository.findById(anyInt())).thenReturn(workplace);

        Workplace result = workplaceService.getWorkplaceById("1");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllWorkplaces() throws SQLException {
        List<Workplace> workplaces = List.of(new Workplace());
        when(workplaceRepository.findAll()).thenReturn(workplaces);

        List<Workplace> result = workplaceService.getAllWorkplaces();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(workplaceRepository, times(1)).findAll();
    }

    @Test
    void testUpdateWorkplaceSuccess() throws SQLException {
        when(workplaceRepository.update(anyInt(), anyString())).thenReturn(new Workplace());

        Workplace result = workplaceService.updateWorkplace("1", workplaceDTO);
        assertNotNull(result);
    }

    @Test
    void testDeleteWorkplaceSuccess() throws SQLException {
        Workplace workplace = new Workplace();
        when(workplaceRepository.deleteById(anyInt())).thenReturn(workplace);

        Workplace result = workplaceService.deleteWorkplace("1");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).deleteById(anyInt());
    }

}
