package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ConferenceHallMapper {

    ConferenceHallMapper INSTANCE = Mappers.getMapper(ConferenceHallMapper.class);

    ConferenceHallDTO conferenceHallToConferenceHallDTO(ConferenceHall conferenceHall);

    ConferenceHall conferenceHallDTOToConferenceHall(ConferenceHallDTO conferenceHallDTO);

    List<ConferenceHallDTO> conferenceHallsToConferenceHallDTOs(List<ConferenceHall> conferenceHalls);
}
