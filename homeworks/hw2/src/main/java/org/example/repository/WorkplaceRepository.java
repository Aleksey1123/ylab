package org.example.repository;

import org.example.entity.Workplace;

import java.util.List;

public interface WorkplaceRepository {

    Workplace save(String description);
    Workplace update(Integer id, String description);
    Workplace findById(Integer workplaceId);
    List<Workplace> findAll();
    Workplace deleteById(Integer workplaceId);
}
