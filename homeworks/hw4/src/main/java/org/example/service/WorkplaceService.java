package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.annotation.Loggable;
import org.example.entity.Workplace;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InvalidIdTypeException;
import org.example.model.WorkplaceDTO;
import org.example.repository.WorkplaceRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/** This service corresponds for work with Workplace Entities. **/
@Service
@RequiredArgsConstructor
public class WorkplaceService {

    private final WorkplaceRepository repository;

    /** This method creates and returns a new workplace with specific description and size. Throws
     * a NumberFormatException if the hall size is not a number. **/
    @Loggable
    public Workplace createWorkplace(WorkplaceDTO workplaceDTO) throws SQLException {

        return repository.save(workplaceDTO);
    }

    /** This method returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    @Loggable
    public Workplace getWorkplaceById(String workplaceId) {

        try {
            Workplace workplace = repository.findById(Integer.valueOf(workplaceId));
            if (workplace == null) {
                throw new EntityNotFoundException("Workplace with id: " + workplaceId + " not found.");
            }

            return workplace;
        }
        catch (NumberFormatException | SQLException e) {
            throw new InvalidIdTypeException("Workplace id must be an Integer type.");
        }
    }

    /** This method returns existing workplace from a database. This method outputs
     * all existing workplaces. **/
    @Loggable
    public List<Workplace> getAllWorkplaces() throws SQLException {

        return repository.findAll();
    }

    /** This method updates and returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    @Loggable
    public Workplace updateWorkplace(String workspaceId, WorkplaceDTO workplaceDTO) throws SQLException {

        int idInt;
        String description = workplaceDTO.getDescription();

        try {
            idInt = Integer.parseInt(workspaceId);
        }
        catch (NumberFormatException e) {
            throw new InvalidIdTypeException("Workplace id must be an Integer type.");
        }

        return repository.update(idInt, description);
    }

    /** This method deletes and returns existing workplace from a database. This method outputs
     * error if no workplace with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    @Loggable
    public Workplace deleteWorkplace(String workspaceId) throws SQLException {

        try {
            int idInt = Integer.parseInt(workspaceId);
            return repository.deleteById(idInt);
        }
        catch (NumberFormatException e) {
            throw new InvalidIdTypeException("Workplace id must be an Integer type.");
        }
    }
}
