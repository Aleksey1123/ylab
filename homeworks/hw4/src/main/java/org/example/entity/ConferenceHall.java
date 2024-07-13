package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entity class for storing and creating new conference halls. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceHall {
    private Integer id;
    private String description;
    private Integer size;
}
