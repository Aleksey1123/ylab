package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.model.ConferenceHallDTO;

public class ConferenceHallMapperImpl implements ConferenceHallMapper {
    public ConferenceHallMapperImpl() {
    }

    public ConferenceHallDTO conferenceHallToConferenceHallDTO(ConferenceHall conferenceHall) {
        if (conferenceHall == null) {
            return null;
        } else {
            ConferenceHallDTO.ConferenceHallDTOBuilder conferenceHallDTO = ConferenceHallDTO.builder();
            conferenceHallDTO.id(conferenceHall.getId());
            conferenceHallDTO.description(conferenceHall.getDescription());
            conferenceHallDTO.size(conferenceHall.getSize());
            return conferenceHallDTO.build();
        }
    }

    public ConferenceHall conferenceHallDTOToConferenceHall(ConferenceHallDTO conferenceHallDTO) {
        if (conferenceHallDTO == null) {
            return null;
        } else {
            ConferenceHall.ConferenceHallBuilder conferenceHall = ConferenceHall.builder();
            conferenceHall.id(conferenceHallDTO.getId());
            conferenceHall.description(conferenceHallDTO.getDescription());
            conferenceHall.size(conferenceHallDTO.getSize());
            return conferenceHall.build();
        }
    }
}
