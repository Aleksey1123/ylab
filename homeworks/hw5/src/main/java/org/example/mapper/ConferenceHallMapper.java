package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConferenceHallMapper {


    ConferenceHallDTO conferenceHallToConferenceHallDTO(ConferenceHall conferenceHall);

    ConferenceHall conferenceHallDTOToConferenceHall(ConferenceHallDTO conferenceHallDTO);

    List<ConferenceHallDTO> conferenceHallsToConferenceHallDTOs(List<ConferenceHall> conferenceHalls);
}
