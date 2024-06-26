package org.example.repository;

import org.example.entity.Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkplaceRepositoryTest {

    private WorkplaceRepository repository;

    @BeforeEach
    void setUp() {
        repository = new WorkplaceRepositoryImpl();
    }

    @Test
    void save() {

        int initialSize = repository.findAll().size();
        repository.save("Test workplace");
        assertThat(repository.findAll()).hasSize(initialSize + 1);
    }

    @Test
    void findById() {

        Workplace actualWorkplace = repository.findAll().get(0);
        Workplace returnedWorkplace = repository.findById(actualWorkplace.getId().toString());
        assertThat(actualWorkplace).isEqualTo(returnedWorkplace);
    }

    @Test
    void findAll() {

        List<Workplace> workplaces = repository.findAll();
        assertThat(workplaces).hasSize(1);
    }

    @Test
    void deleteById() {

        int initialSize = repository.findAll().size();
        repository.save("Test workplace");
        Workplace workplace = repository.findAll().get(initialSize);

        repository.deleteById(workplace.getId().toString());
        assertThat(repository.findAll()).hasSize(initialSize);
    }
}