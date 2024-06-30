package org.example.service;

import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepositoryJDBC;

import java.util.List;

/** This service corresponds for work with Workplace Entities. **/
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

        try {
            Workplace workplace = repository.findById(workplaceId);
            if (workplace == null)
                throw new RuntimeException("No such workplace exists, please try again!");

            return workplace;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Workplace> getAllWorkplaces() {

        return repository.findAll();
    }

    public Workplace updateWorkplace(String id, String description) {

        try {
            if (repository.findById(id) == null)
                throw new RuntimeException("Workplace with ID: " + id + " does not exist.");

            return repository.update(id, description);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Workplace deleteWorkplace(String id) {

        try {
            Workplace workplace = repository.deleteById(id);
            if (workplace == null)
                throw new RuntimeException("Workplace with ID: " + id + " does not exist.");

            return workplace;
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
