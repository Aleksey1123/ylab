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

    /** This method creates and returns a new workplace with specific description and size. Throws
     * a NumberFormatException if the hall size is not a number. **/
    public Workplace createWorkplace(String description) {

        return repository.save(description);
    }

    /** This method returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    public Workplace getWorkplaceById(String workplaceId) {

        try {
            Workplace workplace = repository.findById(Integer.valueOf(workplaceId));
            if (workplace == null)
                System.out.println("No such workplace exists, please try again!");

            return workplace;
        }
        catch (NumberFormatException e) {
            System.out.println("Workplace id must be an integer!");
        }

        return null;
    }

    /** This method returns existing workplace from a database. This method outputs
     * all existing workplaces. **/
    public List<Workplace> getAllWorkplaces() {

        return repository.findAll();
    }

    /** This method updates and returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
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
            System.out.println("Workplace id must be an integer!");
        }

        return null;
    }

    /** This method deletes and returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
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
            System.out.println("Workplace id must be an integer!");
        }

        return null;
    }
}
