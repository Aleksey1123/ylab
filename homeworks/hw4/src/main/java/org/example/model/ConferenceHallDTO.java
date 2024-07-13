package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/** DTO class for user interactions with the conferenceHall entity. **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceHallDTO {
    private Integer id;

    @NotBlank(message = "Provide a description")
    private String description;

    @NotBlank(message = "Provide a size")
    private String size;
}
