//package org.example.service;
//
//import org.example.entity.ConferenceHall;
//import org.example.repository.ConferenceHallRepository;
//import org.example.repository.ConferenceHallRepositoryJDBC;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//class ConferenceHallServiceTest {
//
//    @Mock
//    private ConferenceHallRepositoryJDBC repository;
//    @InjectMocks
//    private ConferenceHallService service;
//
//    private final String id = UUID.randomUUID().toString();
//    private final String testDescription = "test";
//    private final int testSize = 10;
//
//    private final ConferenceHall testHall = ConferenceHall.builder()
//            .id(UUID.randomUUID())
//            .description(testDescription)
//            .size(testSize)
//            .build();
//
//    @Test
//    void testCreateConferenceHall() {
//
//        when(repository.save(any(), any())).thenReturn(null);
//        ConferenceHall hall = service.createConferenceHall(testDescription, testSize);
//        assertThat(hall).isEqualTo(null);
//    }

//    @Test
//    void testGetConferenceHallById() {
//
//        when(repository.findById(any())).thenReturn(testHall);
//        ConferenceHall hall = service.getConferenceHallById(id);
//        assertThat(hall).isEqualTo(testHall);
//    }

//    @Test
//    void testGetAllConferenceHalls() {
//
//        when(repository.findAll()).thenReturn(List.of(testHall));
//        List<ConferenceHall> halls = service.getAllConferenceHalls();
//        assertThat(halls).hasSize(1);
//        assertThat(halls.get(0)).isEqualTo(testHall);
//    }

//    @Test
//    void testUpdateConferenceHall() {
//
//        String newDescription = "new";
//        int newSize = 1;
//
//        when(repository.findById(any())).thenReturn(testHall);
//
//        ConferenceHall hall = service.updateConferenceHall(id, newDescription, newSize);
//        assertThat(hall.getDescription()).isEqualTo(newDescription);
//        assertThat(hall.getSize()).isEqualTo(newSize);
//    }

//    @Test
//    void testDeleteConferenceHall() {
//
//        when(repository.deleteById(any())).thenReturn(null);
//        ConferenceHall hall = service.deleteConferenceHall(id);
//        assertThat(hall).isEqualTo(null);
//    }
//}