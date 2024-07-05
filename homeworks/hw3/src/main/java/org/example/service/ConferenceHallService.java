package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.repository.ConferenceHallRepositoryJDBC;

import java.util.List;

/** This service corresponds for work with ConferenceHall Entities. **/
public class ConferenceHallService {

    protected ConferenceHallRepositoryJDBC repository;

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

    public ConferenceHall getConferenceHallById(String hallId) {

        try {
            ConferenceHall hall = repository.findById(Integer.valueOf(hallId));
            if (hall == null)
                System.out.println("No such conference-hall exists, please try again!");

            return hall;
        }
        catch (NumberFormatException e) {
            System.out.println("Conference-hall id must be an Integer type.");
        }

        return null;
    }

    public List<ConferenceHall> getAllConferenceHalls() {

        return repository.findAll();
    }

    public ConferenceHall updateConferenceHall(String hallId, String description, String size) {

        int hallIdInt;
        int sizeInt;
        try {
            hallIdInt = Integer.parseInt(hallId);
        }
        catch (NumberFormatException e) {
            System.out.println("Conference-hall id must be an Integer type.");
            return null;
        }

        try {
            sizeInt = Integer.parseInt(size);
        }
        catch (NumberFormatException e) {
            System.out.println("Conference-hall size must be an Integer type.");
            return null;
        }

        if (repository.findById(hallIdInt) == null) {
            System.out.println("Conference-hall with ID: " + hallId + " does not exist.");
            return null;
        }

        return repository.update(hallIdInt, description, sizeInt);
    }

    public ConferenceHall deleteConferenceHall(String hallId) {

        try {
            int idInt = Integer.parseInt(hallId);
            ConferenceHall hall = repository.deleteById(idInt);
            if (hall == null) {
                System.out.println("Conference-hall with ID: " + hallId + " does not exist.");
                return null;
            }

            return hall;
        }
        catch (NumberFormatException e) {
            System.out.println("Conference-hall id must be an Integer type.");
        }

        return null;
    }
}
