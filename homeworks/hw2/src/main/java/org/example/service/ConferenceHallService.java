package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.repository.ConferenceHallRepositoryJDBC;

import java.util.List;

/** This service corresponds for work with ConferenceHall Entities. **/
public class ConferenceHallService {

    private ConferenceHallRepositoryJDBC repository;

    public ConferenceHallService() {
        this.repository = new ConferenceHallRepositoryJDBC();
    }

    public ConferenceHall createConferenceHall(String description, String size) {

        try {
            return repository.save(description, Integer.valueOf(size));
        }
        catch (NumberFormatException exception) {
            System.out.println("Conference hall size must be an integer!");
        }

        return null;
    }

    public ConferenceHall getConferenceHallById(String id) {

        try {
            ConferenceHall hall = repository.findById(id);
            if (hall == null)
                throw new RuntimeException("No such conference-hall exists, please try again!");

            return hall;
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<ConferenceHall> getAllConferenceHalls() {

        return repository.findAll();
    }

    public ConferenceHall updateConferenceHall(String id, String description, String size) {

        try {
            if (repository.findById(id) == null)
                throw new RuntimeException("Conference-hall with ID: " + id + " does not exist.");

            return repository.update(id, description, Integer.parseInt(size));
        }
        catch (NumberFormatException e) {
            System.out.println("Conference hall size must be an integer!");
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public ConferenceHall deleteConferenceHall(String id) {

        try {
            ConferenceHall hall = repository.deleteById(id);
            if (hall == null)
                throw new RuntimeException("Conference-hall with ID: " + id + " does not exist.");

            return hall;
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
