package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceHallDTO {

    private Integer id;

    @NotBlank(message = "Provide a description")
    private String description;

    @NotBlank(message = "Provide a size")
    private Integer size;
}
