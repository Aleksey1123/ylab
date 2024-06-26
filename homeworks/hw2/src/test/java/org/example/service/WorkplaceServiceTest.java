package org.example.service;

import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkplaceServiceTest {

    @Mock
    private WorkplaceRepository repository;
    @InjectMocks
    private WorkplaceService service;

    private final String id = UUID.randomUUID().toString();
    private final String testDescription = "test";

    private final Workplace testWorkplace = Workplace.builder()
            .id(UUID.randomUUID())
            .description(testDescription)
            .build();

    @Test
    void testCreateWorkplace() {

        when(repository.save(any())).thenReturn(null);
        Workplace workplace = service.createWorkplace(testDescription);
        assertThat(workplace).isEqualTo(null);
    }

    @Test
    void testGetWorkplaceById() {

        when(repository.findById(any())).thenReturn(testWorkplace);
        Workplace workplace = service.getWorkplaceById(id);
        assertThat(workplace).isEqualTo(testWorkplace);
    }

    @Test
    void testGetAllWorkplaces() {

        when(repository.findAll()).thenReturn(List.of(testWorkplace));
        List<Workplace> workplaces = service.getAllWorkplaces();
        assertThat(workplaces).hasSize(1);
        assertThat(workplaces.get(0)).isEqualTo(testWorkplace);
    }

    @Test
    void testUpdateWorkplace() {

        String newDescription = "new";

        when(repository.findById(any())).thenReturn(testWorkplace);
        Workplace workplace = service.updateWorkplace(id, newDescription);
        assertThat(workplace.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void testDeleteWorkplace() {

        when(repository.deleteById(any())).thenReturn(null);
        Workplace workplace = service.deleteWorkplace(id);
        assertThat(workplace).isEqualTo(null);
    }
}