package org.example.repository;

import org.example.entity.ConferenceHall;

import java.util.*;

public class ConferenceHallRepositoryImpl implements ConferenceHallRepository {

    private final Map<String, ConferenceHall> conferenceHalls;

    public ConferenceHallRepositoryImpl() {
        this.conferenceHalls = new HashMap<>();
        UUID hallId = UUID.randomUUID();
        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(hallId)
                .description("Стандартный конференц-зал")
                .size(12)
                .build();
        conferenceHalls.put(hallId.toString(), conferenceHall);
    }

    @Override
    public ConferenceHall save(String description, Integer size) {
        UUID id = UUID.randomUUID();

        ConferenceHall conferenceHall = ConferenceHall.builder()
                .id(id)
                .description(description)
                .size(size)
                .build();

        return conferenceHalls.put(id.toString(), conferenceHall);
    }

    @Override
    public ConferenceHall findById(String hallId) {
        return conferenceHalls.get(hallId);
    }

    @Override
    public List<ConferenceHall> findAll() {
        return new ArrayList<>(conferenceHalls.values());
    }

    @Override
    public ConferenceHall deleteById(String hallId) {
        return conferenceHalls.remove(hallId);
    }
}
