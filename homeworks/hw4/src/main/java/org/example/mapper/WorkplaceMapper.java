package org.example.mapper;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface WorkplaceMapper {

    WorkplaceMapper INSTANCE = Mappers.getMapper(WorkplaceMapper.class);

    WorkplaceDTO workplaceToWorkplaceDTO(Workplace workplace);

    Workplace workplaceDTOToWorkplace(WorkplaceDTO workplaceDTO);

    List<WorkplaceDTO> workplacesToWorkplaceDTOs(List<Workplace> workplaces);

    List<Workplace> workplaceDTOsToWorkplaces(List<WorkplaceDTO> workplaceDTOs);
}
