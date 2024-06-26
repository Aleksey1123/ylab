package org.example.service;

import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepository;
import org.example.repository.WorkplaceRepositoryImpl;

import java.util.List;

/** Данный сервис отвечает за работу с сущностями Workplace и ConferenceHall(Ресурсы).
 *  Все рабочие места хранятся в HashMap workplaces (хранятся как <workplaceId, workplace>).
 *  Все конференц-залы хранятся в HashMap conferenceHalls (хранятся как <hallId, conferenceHall>).
 *  В сервисе присутствует конструктор, который инициализирует обе HashMap
 *  и создаёт одно рабочее место и конференц-зал.
 **/
public class WorkplaceService {

    private WorkplaceRepository repository;

    public WorkplaceService() {
        repository = new WorkplaceRepositoryImpl();
    }

    // Методы для управления рабочими местами
    public Workplace createWorkplace(String description) {

        return repository.save(description);
    }

    public Workplace getWorkplaceById(String workplaceId) {

        return repository.findById(workplaceId);
    }

    public List<Workplace> getAllWorkplaces() {

        return repository.findAll();
    }

    public Workplace updateWorkplace(String id, String description) {

        Workplace workplace = repository.findById(id);

        if (workplace != null) {
            workplace.setDescription(description);
        }

        return workplace;
    }

    public Workplace deleteWorkplace(String id) {

        return repository.deleteById(id);
    }
}
