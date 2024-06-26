package org.example.repository;

import org.example.entity.Workplace;

import java.util.List;

public interface WorkplaceRepository {

    Workplace save(String description);
    Workplace findById(String workplaceId);
    List<Workplace> findAll();
    Workplace deleteById(String workplaceId);
}
