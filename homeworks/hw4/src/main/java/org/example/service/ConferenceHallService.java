package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.ConferenceHall;
import org.example.exception.EntityNotFoundException;
import org.example.exception.InvalidIdTypeException;
import org.example.exception.InvalidSizeTypeException;
import org.example.model.ConferenceHallDTO;
import org.example.repository.ConferenceHallRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** This service corresponds for work with ConferenceHall Entities. **/
@Service
@RequiredArgsConstructor
public class ConferenceHallService {

    private final ConferenceHallRepository repository;

    /** This method creates and returns a new conferenceHall with specific description and size. Throws
     * a NumberFormatException if the hall size is not a number. **/
    public ConferenceHall createConferenceHall(ConferenceHallDTO conferenceHallDTO) throws SQLException {

        try {
            Integer.parseInt(conferenceHallDTO.getSize());
            return repository.save(conferenceHallDTO);
        }
        catch (NumberFormatException exception) {
            throw new InvalidSizeTypeException("Conference-hall size must be an integer.");
        }
    }

    /** This method returns existing conferenceHall from a database. This method outputs
     * error if no conferenceHall with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    public ConferenceHall getConferenceHallById(String hallId) throws SQLException {

        ConferenceHall hall;
        try {
            hall = repository.findById(Integer.valueOf(hallId));
            if (hall == null) {
                throw new EntityNotFoundException("Conference-hall with id: " + hallId + " not found.");
            }
        }
        catch (NumberFormatException e) {
            throw new InvalidIdTypeException("Conference-hall id must be an Integer type.");
        }

        return hall;
    }

    /** This method returns existing conferenceHall from a database. This method outputs
    * all existing conferenceHalls. **/
    public List<ConferenceHall> getAllConferenceHalls() throws SQLException {

        return repository.findAll();
    }

    /** This method updates and returns existing conferenceHall from a database. This method outputs
     * error if no conferenceHall with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    public ConferenceHall updateConferenceHall(String hallId, ConferenceHallDTO conferenceHallDTO) throws SQLException {

        int hallIdInt;
        int sizeInt;
        String description = conferenceHallDTO.getDescription();
        String size = conferenceHallDTO.getSize();

        try {
            hallIdInt = Integer.parseInt(hallId);
        }
        catch (NumberFormatException e) {
            throw new InvalidIdTypeException("Conference-hall id must be an Integer type.");
        }

        try {
            sizeInt = Integer.parseInt(size);
        }
        catch (NumberFormatException e) {
            throw new InvalidSizeTypeException("Conference-hall size must be an Integer type.");
        }

        return repository.update(hallIdInt, description, sizeInt);
    }

    /** This method deletes and returns existing conferenceHall from a database. This method outputs
     * error if no conferenceHall with such id exists or throws
     * a NumberFormatException if the hall size is not a number. **/
    public ConferenceHall deleteConferenceHall(String hallId) throws SQLException {

        try {
            int idInt = Integer.parseInt(hallId);
            return repository.deleteById(idInt);
        }
        catch (NumberFormatException e) {
            throw new InvalidIdTypeException("Conference-hall id must be an Integer type.");
        }
    }
}
