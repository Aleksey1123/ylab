package org.example.service;

import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepositoryJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WorkplaceServiceTest {

    private WorkplaceService workplaceService;
    private WorkplaceRepositoryJDBC workplaceRepository;

    @BeforeEach
    void setUp() {
        workplaceRepository = mock(WorkplaceRepositoryJDBC.class);
        workplaceService = new WorkplaceService();
        workplaceService.repository = workplaceRepository;
    }

    @Test
    void testCreateWorkplaceSuccess() {
        Workplace workplace = new Workplace();
        when(workplaceRepository.save(anyString())).thenReturn(workplace);

        Workplace result = workplaceService.createWorkplace("description");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).save(anyString());
    }

    @Test
    void testGetWorkplaceByIdSuccess() {
        Workplace workplace = new Workplace();
        when(workplaceRepository.findById(anyInt())).thenReturn(workplace);

        Workplace result = workplaceService.getWorkplaceById("1");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetWorkplaceByIdNotFound() {
        when(workplaceRepository.findById(anyInt())).thenReturn(null);

        Workplace result = workplaceService.getWorkplaceById("1");
        assertNull(result);
        verify(workplaceRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetWorkplaceByIdInvalidId() {
        Workplace result = workplaceService.getWorkplaceById("invalid");
        assertNull(result);
        verify(workplaceRepository, times(0)).findById(anyInt());
    }

    @Test
    void testGetAllWorkplaces() {
        List<Workplace> workplaces = List.of(new Workplace());
        when(workplaceRepository.findAll()).thenReturn(workplaces);

        List<Workplace> result = workplaceService.getAllWorkplaces();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(workplaceRepository, times(1)).findAll();
    }

    @Test
    void testUpdateWorkplaceSuccess() {
        Workplace workplace = new Workplace();
        when(workplaceRepository.findById(anyInt())).thenReturn(workplace);
        when(workplaceRepository.update(anyInt(), anyString())).thenReturn(workplace);

        Workplace result = workplaceService.updateWorkplace("1", "new description");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).findById(anyInt());
        verify(workplaceRepository, times(1)).update(anyInt(), anyString());
    }

    @Test
    void testUpdateWorkplaceNotFound() {
        when(workplaceRepository.findById(anyInt())).thenReturn(null);

        Workplace result = workplaceService.updateWorkplace("1", "new description");
        assertNull(result);
        verify(workplaceRepository, times(1)).findById(anyInt());
        verify(workplaceRepository, times(0)).update(anyInt(), anyString());
    }

    @Test
    void testUpdateWorkplaceInvalidId() {
        Workplace result = workplaceService.updateWorkplace("invalid", "new description");
        assertNull(result);
        verify(workplaceRepository, times(0)).findById(anyInt());
        verify(workplaceRepository, times(0)).update(anyInt(), anyString());
    }

    @Test
    void testDeleteWorkplaceSuccess() {
        Workplace workplace = new Workplace();
        when(workplaceRepository.deleteById(anyInt())).thenReturn(workplace);

        Workplace result = workplaceService.deleteWorkplace("1");
        assertNotNull(result);
        verify(workplaceRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteWorkplaceNotFound() {
        when(workplaceRepository.deleteById(anyInt())).thenReturn(null);

        Workplace result = workplaceService.deleteWorkplace("1");
        assertNull(result);
        verify(workplaceRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteWorkplaceInvalidId() {
        Workplace result = workplaceService.deleteWorkplace("invalid");
        assertNull(result);
        verify(workplaceRepository, times(0)).deleteById(anyInt());
    }
}
