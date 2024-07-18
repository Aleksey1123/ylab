package org.example.repository;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;

import java.sql.SQLException;
import java.util.List;

/** An interface for WorkplaceRepositoryJDBC **/
public interface WorkplaceRepository {

    Workplace save(WorkplaceDTO workplaceDTO) throws SQLException;
    Workplace update(Integer id, String description) throws SQLException;
    Workplace findById(Integer workplaceId) throws SQLException;
    List<Workplace> findAll() throws SQLException;
    Workplace deleteById(Integer workplaceId) throws SQLException;
}
