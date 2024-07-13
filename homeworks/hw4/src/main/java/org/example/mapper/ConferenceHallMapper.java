package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConferenceHallMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "size", target = "size")
    ConferenceHallDTO conferenceHallToConferenceHallDTO(ConferenceHall conferenceHall);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "size", target = "size")
    ConferenceHall conferenceHallDTOToConferenceHall(ConferenceHallDTO conferenceHallDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "size", target = "size")
    List<ConferenceHallDTO> conferenceHallsToConferenceHallDTOs(List<ConferenceHall> conferenceHalls);
}
