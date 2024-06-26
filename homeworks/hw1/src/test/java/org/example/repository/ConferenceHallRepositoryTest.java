package org.example.repository;

import org.example.entity.ConferenceHall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConferenceHallRepositoryTest {

    private ConferenceHallRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ConferenceHallRepositoryImpl();
    }

    @Test
    void save() {

        int initialSize = repository.findAll().size();
        repository.save("Test hall", 100);
        assertThat(repository.findAll()).hasSize(initialSize + 1);
    }

    @Test
    void findById() {

        int initialSize = repository.findAll().size();
        repository.save("Test hall", 100);
        assertThat(repository.findAll()).hasSize(initialSize + 1);
    }

    @Test
    void findAll() {

        List<ConferenceHall> halls = repository.findAll();
        assertThat(halls).hasSize(1);
    }

    @Test
    void deleteById() {

        int initialSize = repository.findAll().size();
        repository.save("Test hall", 100);
        ConferenceHall hall = repository.findAll().get(initialSize);

        repository.deleteById(hall.getId().toString());
        assertThat(repository.findAll()).hasSize(initialSize);
    }
}