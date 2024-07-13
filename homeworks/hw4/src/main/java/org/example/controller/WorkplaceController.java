package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Workplace;
import org.example.mapper.WorkplaceMapper;
import org.example.model.WorkplaceDTO;
import org.example.service.WorkplaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/workplace")
@RequiredArgsConstructor
public class WorkplaceController {

    private final WorkplaceService workplaceService;
    private final WorkplaceMapper workplaceMapper;

    private ResponseEntity<WorkplaceDTO> mapWorkplace(Workplace workplace) {

        WorkplaceDTO workplaceDTO = workplaceMapper.workplaceToWorkplaceDTO(workplace);
        return ResponseEntity.ok(workplaceDTO);
    }

    /** This method returns a workplace specified by id parameter or returns
     * all existing workplaces if id parameter is null.
     * Requested Parameter:
     * ?id=******* - id of any existing workplace
     * or it will return all existing workplaces
     *
     * **/
    @GetMapping("/{id}")
    public ResponseEntity<WorkplaceDTO> getWorkplaceById(@PathVariable("id") String id) {

        Workplace workplace = workplaceService.getWorkplaceById(id);
        return mapWorkplace(workplace);
    }

    @GetMapping
    public ResponseEntity<List<WorkplaceDTO>> getAllWorkplaces() throws SQLException {

        List<Workplace> workplaces = workplaceService.getAllWorkplaces();
        List<WorkplaceDTO> workplaceDTOs = workplaceMapper.workplacesToWorkplaceDTOs(workplaces);
        return ResponseEntity.ok(workplaceDTOs);
    }

    /** This method creates a new workplace via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of a future workplace
     * }
     *
     * **/
    @PostMapping
    public ResponseEntity<WorkplaceDTO> createWorkplace(@RequestBody WorkplaceDTO workplaceDTO) throws SQLException {

        Workplace savedWorkplace = workplaceService.createWorkplace(workplaceDTO);
        return mapWorkplace(savedWorkplace);
    }

    /** This method updates an existing workplace via request body data.
     *  Requested Body:
     * {
     *     "description": ******* - description of an existing workplace
     * }
     *
     * **/
    @PutMapping("/{id}")
    public ResponseEntity<WorkplaceDTO> updateWorkplace(
            @PathVariable("id") String id,
            @RequestBody WorkplaceDTO workplaceDTO
    ) throws SQLException {

        Workplace updatedWorkplace = workplaceService.updateWorkplace(id, workplaceDTO);
        return mapWorkplace(updatedWorkplace);
    }

    /** This method deletes a workplace specified by id.
     * Requested Parameter:
     * ?id=******* - id of an existing workplace
     *
     * **/
    @DeleteMapping("/{id}")
    public ResponseEntity<WorkplaceDTO> deleteWorkplace(@PathVariable("id") String id) throws SQLException {

        Workplace deletedWorkplace = workplaceService.deleteWorkplace(id);
        return mapWorkplace(deletedWorkplace);
    }
}
