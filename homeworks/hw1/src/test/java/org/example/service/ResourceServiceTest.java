package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.entity.Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {

    private ResourceService service;

    @BeforeEach
    void setUp() {

        service = new ResourceService();
    }

    @Test
    void testCreateWorkplace() {

        int initialSize = service.getAllWorkplaces().size();
        service.createWorkplace("Test workplace");
        assertThat(service.getAllWorkplaces()).hasSize(initialSize + 1);
    }

    @Test
    void testGetWorkplaceById() {

        Workplace actualWorkplace = service.getAllWorkplaces().get(0);
        Workplace returnedWorkplace = service.getWorkplaceById(actualWorkplace.getId().toString());
        assertThat(actualWorkplace).isEqualTo(returnedWorkplace);
    }

    @Test
    void testGetAllWorkplaces() {

        List<Workplace> workplaces = service.getAllWorkplaces();
        assertThat(workplaces).hasSize(1);
    }

    @Test
    void testUpdateWorkplace() {

        Workplace workplace = service.getAllWorkplaces().get(0);
        String initDescription = workplace.getDescription();
        String testDescription = "Test hall";

        Workplace updatedWorkplace = service.updateWorkplace(workplace.getId().toString(), testDescription);
        String newDescription = updatedWorkplace.getDescription();

        assertThat(newDescription).isNotEqualTo(initDescription);
        assertThat(newDescription).isEqualTo(testDescription);

    }

    @Test
    void testDeleteWorkplace() {

        int initialSize = service.getAllWorkplaces().size();
        service.createWorkplace("Test workplace");
        Workplace workplace = service.getAllWorkplaces().get(initialSize);

        service.deleteWorkplace(workplace.getId().toString());
        assertThat(service.getAllWorkplaces()).hasSize(initialSize);
    }

    @Test
    void testCreateConferenceHall() {

        int initialSize = service.getAllConferenceHalls().size();
        service.createConferenceHall("Test hall", 100);
        assertThat(service.getAllConferenceHalls()).hasSize(initialSize + 1);
    }

    @Test
    void testGetConferenceHallById() {

        ConferenceHall actualHall = service.getAllConferenceHalls().get(0);
        ConferenceHall returnedHall = service.getConferenceHallById(actualHall.getId().toString());
        assertThat(actualHall).isEqualTo(returnedHall);
    }

    @Test
    void testGetAllConferenceHalls() {

        List<ConferenceHall> halls = service.getAllConferenceHalls();
        assertThat(halls).hasSize(1);
    }

    @Test
    void testUpdateConferenceHall() {

        ConferenceHall hall = service.getAllConferenceHalls().get(0);
        String initDescription = hall.getDescription();
        int initSize = hall.getSize();
        String testDescription = "Test hall";
        int testSize = 100;

        ConferenceHall updatedHall = service.updateConferenceHall(hall.getId().toString(), testDescription, testSize);
        String newDescription = updatedHall.getDescription();
        int newSize = updatedHall.getSize();

        assertThat(newDescription).isNotEqualTo(initDescription);
        assertThat(newDescription).isEqualTo(testDescription);

        assertThat(newSize).isNotEqualTo(initSize);
        assertThat(newSize).isEqualTo(testSize);
    }

    @Test
    void testDeleteConferenceHall() {

        int initialSize = service.getAllConferenceHalls().size();
        service.createConferenceHall("Test hall", 100);
        ConferenceHall hall = service.getAllConferenceHalls().get(initialSize);

        service.deleteConferenceHall(hall.getId().toString());
        assertThat(service.getAllConferenceHalls()).hasSize(initialSize);
    }
}