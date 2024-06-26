package org.example.repository;

import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    private UserRepository repository;

    @BeforeEach
    void setUp() {
       repository = new UserRepositoryImpl();
    }

    @Test
    void testSave() {

        int initialSize = repository.findAll().size();
        repository.save("Test user", "Test password");
        assertThat(repository.findAll()).hasSize(initialSize + 1);
    }

    @Test
    void testFindByUsername() {

        User user = repository.findAll().get("admin");
        User returnedUser = repository.findByUsername(user.getUsername());
        assertThat(user).isEqualTo(returnedUser);
    }

    @Test
    void testFindAll() {

        Map<String, User> users = repository.findAll();
        assertThat(users).hasSize(1);
    }
}