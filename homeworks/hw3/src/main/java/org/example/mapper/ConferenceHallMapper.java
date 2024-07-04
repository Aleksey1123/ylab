package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConferenceHallMapper {

    ConferenceHallMapper INSTANCE = Mappers.getMapper(ConferenceHallMapper.class);

    ConferenceHallDTO conferenceHallToConferenceHallDTO(ConferenceHall conferenceHall);

    ConferenceHall conferenceHallDTOToConferenceHall(ConferenceHallDTO conferenceHallDTO);
}
