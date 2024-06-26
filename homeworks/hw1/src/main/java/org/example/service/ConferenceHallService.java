package org.example.service;

import org.example.entity.ConferenceHall;
import org.example.repository.ConferenceHallRepository;
import org.example.repository.ConferenceHallRepositoryImpl;

import java.util.List;

public class ConferenceHallService {

    private ConferenceHallRepository repository;

    public ConferenceHallService() {
        this.repository = new ConferenceHallRepositoryImpl();
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

        ConferenceHall conferenceHall = repository.findById(id);

        if (conferenceHall != null) {
            conferenceHall.setDescription(description);
            conferenceHall.setSize(size);
        }

        return conferenceHall;
    }

    public ConferenceHall deleteConferenceHall(String id) {

        return repository.deleteById(id);
    }
}
