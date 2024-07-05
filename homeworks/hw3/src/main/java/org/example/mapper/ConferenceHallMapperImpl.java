package org.example.mapper;

import org.example.entity.ConferenceHall;
import org.example.entity.Workplace;
import org.example.model.ConferenceHallDTO;
import org.example.model.WorkplaceDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<ConferenceHallDTO> conferenceHallsToConferenceHallDTOs(List<ConferenceHall> conferenceHalls) {
        if (conferenceHalls == null) {
            return null;
        } else {
            List<ConferenceHallDTO> list = new ArrayList(conferenceHalls.size());
            Iterator var3 = conferenceHalls.iterator();

            while(var3.hasNext()) {
                ConferenceHall conferenceHall = (ConferenceHall) var3.next();
                list.add(this.conferenceHallToConferenceHallDTO(conferenceHall));
            }

            return list;
        }
    }
}
