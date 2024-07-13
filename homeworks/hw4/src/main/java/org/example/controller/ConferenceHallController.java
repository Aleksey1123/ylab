package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.ConferenceHall;
import org.example.mapper.ConferenceHallMapper;
import org.example.model.ConferenceHallDTO;
import org.example.service.ConferenceHallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/conferenceHall")
@RequiredArgsConstructor
public class ConferenceHallController {

    private final ConferenceHallService conferenceHallService;
    private final ConferenceHallMapper conferenceHallMapper;

    private ResponseEntity<ConferenceHallDTO> mapConferenceHall(ConferenceHall conferenceHall) {

        ConferenceHallDTO conferenceHallDTO = conferenceHallMapper
                .conferenceHallToConferenceHallDTO(conferenceHall);
        return ResponseEntity.ok(conferenceHallDTO);
    }

    /** This method returns a ConferenceHall specified by id parameter or returns
     * all existing ConferenceHalls if id parameter is null.
     * Requested Parameter:
     * ?id=******* - id of any existing ConferenceHall
     * or it will return all existing ConferenceHalls
     *
     * **/
    @GetMapping("/{id}")
    public ResponseEntity<ConferenceHallDTO> getConferenceHallById(@PathVariable("id") String id) throws SQLException {

        ConferenceHall conferenceHall = conferenceHallService.getConferenceHallById(id);
        return mapConferenceHall(conferenceHall);
    }

    @GetMapping
    public ResponseEntity<List<ConferenceHallDTO>> getAllConferenceHalls() throws SQLException {

        List<ConferenceHall> conferenceHalls = conferenceHallService.getAllConferenceHalls();
        List<ConferenceHallDTO> conferenceHallDTOs =
                conferenceHallMapper.conferenceHallsToConferenceHallDTOs(conferenceHalls);
        return ResponseEntity.ok(conferenceHallDTOs);
    }

    /** This method creates a new ConferenceHall via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of a future ConferenceHall
     *     "size": ******* - size of an existing ConferenceHall
     * }
     *
     * **/
    @PostMapping
    public ResponseEntity<ConferenceHallDTO> createConferenceHall(@RequestBody ConferenceHallDTO conferenceHallDTO) throws SQLException {

        ConferenceHall savedConferenceHall = conferenceHallService.createConferenceHall(conferenceHallDTO);
        return mapConferenceHall(savedConferenceHall);
    }

    /** This method updates an existing ConferenceHall via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of an existing ConferenceHall
     *     "size": ******* - size of an existing ConferenceHall
     * }
     *
     * **/
    @PutMapping("/{id}")
    public ResponseEntity<ConferenceHallDTO> updateConferenceHall(
            @PathVariable("id") String id,
            @RequestBody ConferenceHallDTO conferenceHallDTO
    ) throws SQLException {

        ConferenceHall updatedConferenceHall = conferenceHallService
                .updateConferenceHall(id, conferenceHallDTO);
        return mapConferenceHall(updatedConferenceHall);
    }

    /** This method deletes a ConferenceHall specified by id.
     * Requested Parameter:
     * ?id=******* - id of an existing ConferenceHall
     *
     * **/
    @DeleteMapping("/{id}")
    public ResponseEntity<ConferenceHallDTO> deleteConferenceHall(@PathVariable("id") String id) throws SQLException {

        ConferenceHall deletedConferenceHall =
                    conferenceHallService.deleteConferenceHall(id);
        return mapConferenceHall(deletedConferenceHall);
    }
}
