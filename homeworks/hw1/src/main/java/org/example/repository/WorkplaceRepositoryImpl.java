package org.example.repository;

import org.example.entity.Workplace;

import java.util.*;

public class WorkplaceRepositoryImpl implements WorkplaceRepository {

    private final Map<String, Workplace> workplaces;

    public WorkplaceRepositoryImpl() {

        this.workplaces = new HashMap<>();

        UUID workplaceId = UUID.randomUUID();
        Workplace workplace = Workplace.builder()
                .id(workplaceId)
                .description("Стандартное рабочее место")
                .build();
        workplaces.put(workplaceId.toString(), workplace);
    }

    @Override
    public Workplace save(String description) {

        UUID id = UUID.randomUUID();

        Workplace workplace = Workplace.builder()
                .id(id)
                .description(description)
                .build();

        return workplaces.put(id.toString(), workplace);
    }

    @Override
    public Workplace findById(String workplaceId) {
        return workplaces.get(workplaceId);
    }

    @Override
    public List<Workplace> findAll() {
        return new ArrayList<>(workplaces.values());
    }

    @Override
    public Workplace deleteById(String workplaceId) {
        return workplaces.remove(workplaceId);
    }
}
