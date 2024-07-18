package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Workplace;
import org.example.mapper.WorkplaceMapper;
import org.example.model.WorkplaceDTO;
import org.example.service.WorkplaceService;
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
public class WorkplaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WorkplaceService workplaceService;

    @Mock
    private WorkplaceMapper workplaceMapper;

    @InjectMocks
    private WorkplaceController workplaceController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(workplaceController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetWorkplaceById() throws Exception {
        Workplace workplace = new Workplace();
        WorkplaceDTO workplaceDTO = new WorkplaceDTO();

        when(workplaceService.getWorkplaceById(anyString())).thenReturn(workplace);
        when(workplaceMapper.workplaceToWorkplaceDTO(any())).thenReturn(workplaceDTO);

        mockMvc.perform(get("/workplace/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllWorkplaces() throws Exception {
        List<Workplace> workplaces = List.of(new Workplace());
        List<WorkplaceDTO> workplaceDTOs = List.of(new WorkplaceDTO());

        when(workplaceService.getAllWorkplaces()).thenReturn(workplaces);
        when(workplaceMapper.workplacesToWorkplaceDTOs(any())).thenReturn(workplaceDTOs);

        mockMvc.perform(get("/workplace")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWorkplace() throws Exception {
        WorkplaceDTO workplaceDTO = new WorkplaceDTO();
        Workplace workplace = new Workplace();

        when(workplaceService.createWorkplace(any())).thenReturn(workplace);
        when(workplaceMapper.workplaceToWorkplaceDTO(any())).thenReturn(workplaceDTO);

        mockMvc.perform(post("/workplace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workplaceDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateWorkplace() throws Exception {
        WorkplaceDTO workplaceDTO = new WorkplaceDTO();
        Workplace workplace = new Workplace();

        when(workplaceService.updateWorkplace(anyString(), any())).thenReturn(workplace);
        when(workplaceMapper.workplaceToWorkplaceDTO(any())).thenReturn(workplaceDTO);

        mockMvc.perform(put("/workplace/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workplaceDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWorkplace() throws Exception {
        Workplace workplace = new Workplace();
        WorkplaceDTO workplaceDTO = new WorkplaceDTO();

        when(workplaceService.deleteWorkplace(anyString())).thenReturn(workplace);
        when(workplaceMapper.workplaceToWorkplaceDTO(any())).thenReturn(workplaceDTO);

        mockMvc.perform(delete("/workplace/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}