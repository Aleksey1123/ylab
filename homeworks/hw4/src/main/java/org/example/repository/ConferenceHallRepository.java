package org.example.repository;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;

import java.sql.SQLException;
import java.util.List;

/** An interface for ConferenceHallRepositoryJDBC **/
public interface ConferenceHallRepository {

    ConferenceHall save(ConferenceHallDTO conferenceHallDTO) throws SQLException;
    ConferenceHall update(Integer hallId, String description, int size) throws SQLException;
    ConferenceHall findById(Integer hallId) throws SQLException;
    List<ConferenceHall> findAll() throws SQLException;
    ConferenceHall deleteById(Integer hallId) throws SQLException;
}
