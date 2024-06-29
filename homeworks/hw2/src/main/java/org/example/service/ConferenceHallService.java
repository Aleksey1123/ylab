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

    public ConferenceHall createConferenceHall(String description, int size) {

        return repository.save(description, size);
    }

    public ConferenceHall getConferenceHallById(String id) {
        return repository.findById(id);
    }

    public List<ConferenceHall> getAllConferenceHalls() {

        return repository.findAll();
    }

    public ConferenceHall updateConferenceHall(String id, String description, int size) {

        ConferenceHall newHall = null;

        if (repository.findById(id) != null) {
              newHall = repository.update(id, description, size);
        }

        return newHall;
    }

    public ConferenceHall deleteConferenceHall(String id) {

        return repository.deleteById(id);
    }
}
