package org.example.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.example.entity.Workplace;
import org.example.model.WorkplaceDTO;

public class WorkplaceMapperImpl implements WorkplaceMapper {
    public WorkplaceMapperImpl() {
    }

    public WorkplaceDTO workplaceToWorkplaceDTO(Workplace workplace) {
        if (workplace == null) {
            return null;
        } else {
            WorkplaceDTO.WorkplaceDTOBuilder workplaceDTO = WorkplaceDTO.builder();
            workplaceDTO.id(workplace.getId());
            workplaceDTO.description(workplace.getDescription());
            return workplaceDTO.build();
        }
    }

    public Workplace workplaceDTOToWorkplace(WorkplaceDTO workplaceDTO) {
        if (workplaceDTO == null) {
            return null;
        } else {
            Workplace.WorkplaceBuilder workplace = Workplace.builder();
            workplace.id(workplaceDTO.getId());
            workplace.description(workplaceDTO.getDescription());
            return workplace.build();
        }
    }

    public List<WorkplaceDTO> workplacesToWorkplaceDTOs(List<Workplace> workplaces) {
        if (workplaces == null) {
            return null;
        } else {
            List<WorkplaceDTO> list = new ArrayList(workplaces.size());
            Iterator var3 = workplaces.iterator();

            while(var3.hasNext()) {
                Workplace workplace = (Workplace)var3.next();
                list.add(this.workplaceToWorkplaceDTO(workplace));
            }

            return list;
        }
    }

    public List<Workplace> workplaceDTOsToWorkplaces(List<WorkplaceDTO> workplaceDTOs) {
        if (workplaceDTOs == null) {
            return null;
        } else {
            List<Workplace> list = new ArrayList(workplaceDTOs.size());
            Iterator var3 = workplaceDTOs.iterator();

            while(var3.hasNext()) {
                WorkplaceDTO workplaceDTO = (WorkplaceDTO)var3.next();
                list.add(this.workplaceDTOToWorkplace(workplaceDTO));
            }

            return list;
        }
    }
}
