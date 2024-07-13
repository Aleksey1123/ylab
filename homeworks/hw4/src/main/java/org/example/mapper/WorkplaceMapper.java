package org.example.mapper;

import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    WorkplaceDTO workplaceToWorkplaceDTO(Workplace workplace);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    Workplace workplaceDTOToWorkplace(WorkplaceDTO workplaceDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    List<WorkplaceDTO> workplacesToWorkplaceDTOs(List<Workplace> workplaces);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    List<Workplace> workplaceDTOsToWorkplaces(List<WorkplaceDTO> workplaceDTOs);
}
