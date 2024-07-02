package org.example.repository;

import org.example.entity.ConferenceHall;

import java.util.List;

public interface ConferenceHallRepository {

    ConferenceHall save(String description, Integer size);
    ConferenceHall update(Integer hallId, String description, int size);
    ConferenceHall findById(Integer hallId);
    List<ConferenceHall> findAll();
    ConferenceHall deleteById(Integer hallId);
}
