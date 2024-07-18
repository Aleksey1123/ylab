package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity class for storing and creating new workplaces. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workplace {
    private Integer id;
    private String description;
}
