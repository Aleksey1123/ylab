package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.entity.Workplace;

import java.util.*;

/** Данный сервис отвечает за работу с сущностями Workplace и ConferenceHall(Ресурсы).
 *  Все рабочие места хранятся в HashMap workplaces (хранятся как <workplaceId, workplace>).
 *  Все конференц-залы хранятся в HashMap conferenceHalls (хранятся как <hallId, conferenceHall>).
 *  В сервисе присутствует конструктор, который инициализирует обе HashMap
 *  и создаёт одно рабочее место и конференц-зал.
 **/
public class ResourceService {

    private final Map<String, Workplace> workplaces;
    private final Map<String, ConferenceHall> conferenceHalls;

    public ResourceService() {

        workplaces = new HashMap<>();
        conferenceHalls = new HashMap<>();

        UUID workplaceId = UUID.randomUUID();
        UUID hallId = UUID.randomUUID();

        Workplace workplace = Workplace.builder()
                .id(workplaceId)
                .description("Стандартное рабочее место")
                .build();
        workplaces.put(workplaceId.toString(), workplace);

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(hallId)
                .description("Стандартный конференц-зал")
                .size(12)
                .build();
        conferenceHalls.put(hallId.toString(), conferenceHall);
    }

    // Методы для управления рабочими местами
    public void createWorkplace(String description) {

        UUID id = UUID.randomUUID();

        Workplace workplace = Workplace.builder()
                .id(id)
                .description(description)
                .build();

        workplaces.put(id.toString(), workplace);
    }

    public Workplace getWorkplaceById(String id) {

        return workplaces.get(id);
    }

    public List<Workplace> getAllWorkplaces() {

        return new ArrayList<>(workplaces.values());
    }

    public Workplace updateWorkplace(String id, String description) {

        Workplace workplace = workplaces.get(id);

        if (workplace != null) {
            workplace.setDescription(description);
        }

        return workplace;
    }

    public Workplace deleteWorkplace(String id) {

        return workplaces.remove(id);
    }

    // Методы для управления конференц-залами
    public void createConferenceHall(String description, int size) {

        UUID id = UUID.randomUUID();

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(id)
                .description(description)
                .size(size)
                .build();

        conferenceHalls.put(id.toString(), conferenceHall);
    }

    public ConferenceHall getConferenceHallById(String id) {

        return conferenceHalls.get(id);
    }

    public List<ConferenceHall> getAllConferenceHalls() {

        return new ArrayList<>(conferenceHalls.values());
    }

    public ConferenceHall updateConferenceHall(String id, String description, int size) {

        ConferenceHall conferenceHall = conferenceHalls.get(id);

        if (conferenceHall != null) {
            conferenceHall.setDescription(description);
            conferenceHall.setSize(size);
        }

        return conferenceHall;
    }

    public ConferenceHall deleteConferenceHall(String id) {

        return conferenceHalls.remove(id);
    }
}
