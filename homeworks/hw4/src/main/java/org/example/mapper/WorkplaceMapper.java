package org.example.mapper;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {

    WorkplaceDTO workplaceToWorkplaceDTO(Workplace workplace);

    Workplace workplaceDTOToWorkplace(WorkplaceDTO workplaceDTO);

    List<WorkplaceDTO> workplacesToWorkplaceDTOs(List<Workplace> workplaces);

    List<Workplace> workplaceDTOsToWorkplaces(List<WorkplaceDTO> workplaceDTOs);
}
