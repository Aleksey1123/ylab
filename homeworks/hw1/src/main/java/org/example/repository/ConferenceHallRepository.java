package org.example.repository;

import org.example.entity.ConferenceHall;

import java.util.List;

public interface ConferenceHallRepository {

    ConferenceHall save(String description, Integer size);
    ConferenceHall findById(String hallId);
    List<ConferenceHall> findAll();
    ConferenceHall deleteById(String hallId);
}
