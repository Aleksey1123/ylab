package org.example.service;

import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepositoryJDBC;

import java.util.List;

/** Данный сервис отвечает за работу с сущностями Workplace и ConferenceHall(Ресурсы).
 *  Все рабочие места хранятся в HashMap workplaces (хранятся как <workplaceId, workplace>).
 *  Все конференц-залы хранятся в HashMap conferenceHalls (хранятся как <hallId, conferenceHall>).
 *  В сервисе присутствует конструктор, который инициализирует обе HashMap
 *  и создаёт одно рабочее место и конференц-зал.
 **/
public class WorkplaceService {

    private WorkplaceRepositoryJDBC repository;

    public WorkplaceService() {
        repository = new WorkplaceRepositoryJDBC();
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

        Workplace newWorkplace = null;

        if (repository.findById(id) != null) {
            newWorkplace = repository.update(id, description);
        }

        return newWorkplace;
    }

    public Workplace deleteWorkplace(String id) {

        return repository.deleteById(id);
    }
}
