package org.example.service;

import org.example.annotation.Loggable;
import org.example.entity.Workplace;
import org.example.repository.WorkplaceRepositoryJDBC;

import java.util.List;

/** This service corresponds for work with Workplace Entities. **/
@Loggable
public class WorkplaceService {

    protected WorkplaceRepositoryJDBC repository;

    public WorkplaceService() {
        repository = new WorkplaceRepositoryJDBC();
    }

    // Методы для управления рабочими местами
    public Workplace createWorkplace(String description) {

        return repository.save(description);
    }

    public Workplace getWorkplaceById(String workplaceId) {

        try {
            Workplace workplace = repository.findById(Integer.valueOf(workplaceId));
            if (workplace == null)
                System.out.println("No such workplace exists, please try again!");

            return workplace;
        }
        catch (NumberFormatException e) {
            System.out.println("Workspace id must be an integer!");
        }

        return null;
    }

    public List<Workplace> getAllWorkplaces() {

        return repository.findAll();
    }

    public Workplace updateWorkplace(String workspaceId, String description) {

        try {
            int idInt = Integer.parseInt(workspaceId);
            if (repository.findById(idInt) == null) {
                System.out.println("Workplace with ID: " + workspaceId + " does not exist.");
                return null;
            }

            return repository.update(idInt, description);
        }
        catch (NumberFormatException e) {
            System.out.println("Workspace id must be an integer!");
        }

        return null;
    }

    public Workplace deleteWorkplace(String workspaceId) {

        try {
            int idInt = Integer.parseInt(workspaceId);
            Workplace workplace = repository.deleteById(idInt);
            if (workplace == null) {
                System.out.println("Workplace with ID: " + workspaceId + " does not exist.");
                return null;
            }

            return workplace;
        }
        catch (NumberFormatException e) {
            System.out.println("Workspace id must be an integer!");
        }

        return null;
    }
}
